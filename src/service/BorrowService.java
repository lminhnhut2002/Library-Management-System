/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author PC
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import model.Book;
import model.Member;
import model.BorrowingTransaction;

public class BorrowService {

    private final Map<String, Book> books;
    private final Map<String, Member> members;
    private final List<BorrowingTransaction> transactions;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BorrowService(Map<String, Book> books,
                         Map<String, Member> members,
                         List<BorrowingTransaction> transactions) {
        this.books = books;
        this.members = members;
        this.transactions = transactions;
    }

    public boolean borrowBook(String memberID, String bookID, String borrowDateText) {
        try {
            if (memberID == null || memberID.trim().isEmpty()) {
                throw new IllegalArgumentException("Fail: Member ID must not be empty.");
            }

            if (bookID == null || bookID.trim().isEmpty()) {
                throw new IllegalArgumentException("Fail: Book ID must not be empty.");
            }

            if (borrowDateText == null || borrowDateText.trim().isEmpty()) {
                throw new IllegalArgumentException("Fail: Borrow date must not be empty.");
            }

            Member member = members.get(memberID);
            if (member == null) {
                throw new IllegalArgumentException("Fail: Member not found.");
            }

            Book book = books.get(bookID);
            if (book == null) {
                throw new IllegalArgumentException("Fail: Book not found.");
            }

            LocalDate borrowDate;

            try {
                borrowDate = LocalDate.parse(borrowDateText, FORMATTER);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Fail: Invalid date format. Use DD/MM/YYYY.");
            }

            if (borrowDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Fail: Borrow date must be today or in the past.");
            }

            if (book.getQuantity() <= 0) {
                throw new IllegalStateException("Fail: Book is out of stock.");
            }

            int currentBorrowed = countCurrentBorrowedBooks(memberID);

            if (currentBorrowed >= member.getBorrowLimit()) {
                throw new IllegalStateException("Fail: Borrow limit exceeded.");
            }

            if (isBookAlreadyBorrowedByMember(memberID, bookID)) {
                throw new IllegalStateException("Fail: This member has already borrowed this book.");
            }

            String transactionID = generateTransactionID();

            BorrowingTransaction transaction = new BorrowingTransaction(
                    transactionID,
                    bookID,
                    memberID,
                    borrowDate,
                    null,
                    0
            );

            transactions.add(transaction);

            book.setQuantity(book.getQuantity() - 1);
            book.setBorrowCount(book.getBorrowCount() + 1);

            member.getBorrowedBooks().add(bookID);

            System.out.println("Book '" + book.getTitle()
                    + "' borrowed by '" + member.getName()
                    + "' successfully.");

            return true;

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private int countCurrentBorrowedBooks(String memberID) {
        int count = 0;

        for (BorrowingTransaction transaction : transactions) {
            if (transaction.getMemberID().equals(memberID)
                    && transaction.getReturnDate() == null) {
                count++;
            }
        }

        return count;
    }

    private boolean isBookAlreadyBorrowedByMember(String memberID, String bookID) {
        for (BorrowingTransaction transaction : transactions) {
            if (transaction.getMemberID().equals(memberID)
                    && transaction.getBookID().equals(bookID)
                    && transaction.getReturnDate() == null) {
                return true;
            }
        }

        return false;
    }

    private String generateTransactionID() {
        return "T" + String.format("%04d", transactions.size() + 1);
    }
}