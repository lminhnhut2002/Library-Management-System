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
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Member;
import model.BorrowingTransaction;

public class BorrowService {

    private final List<Book> books;
    private final List<Member> members;
    private final List<BorrowingTransaction> transactions;

    // Vì BorrowingTransaction hiện đang trống nên BorrowService tự lưu tạm giao dịch ở đây
    private final List<BorrowRecord> borrowRecords;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BorrowService(List<Book> books, List<Member> members, List<BorrowingTransaction> transactions) {
        this.books = books;
        this.members = members;
        this.transactions = transactions;
        this.borrowRecords = new ArrayList<>();
    }

    // Chức năng mượn sách
    public boolean borrowBook(String memberID, String bookID, String borrowDateText) {
        try {
            memberID = validateText(memberID, "Member ID");
            bookID = validateText(bookID, "Book ID");
            borrowDateText = validateText(borrowDateText, "Borrow date");

            Member member = findMemberByID(memberID);
            if (member == null) {
                throw new IllegalArgumentException("Fail: Member not found.");
            }

            Book book = findBookByID(bookID);
            if (book == null) {
                throw new IllegalArgumentException("Fail: Book not found.");
            }

            LocalDate borrowDate = parseDate(borrowDateText, "borrow date");

            if (borrowDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Fail: Borrow date must be today or in the past.");
            }

            if (!book.isAvailable()) {
                throw new IllegalStateException("Fail: Book is out of stock.");
            }

            int currentBorrowed = countCurrentBorrowedBooks(memberID);
            int borrowLimit = getBorrowLimit(member);

            if (currentBorrowed >= borrowLimit) {
                throw new IllegalStateException("Fail: Borrow limit exceeded.");
            }

            if (isBookAlreadyBorrowedByMember(memberID, bookID)) {
                throw new IllegalStateException("Fail: This member has already borrowed this book.");
            }

            String transactionID = generateTransactionID();

            BorrowRecord record = new BorrowRecord(transactionID, bookID, memberID, borrowDate);
            borrowRecords.add(record);

            // Book.java đã có sẵn borrowBook(): tự giảm quantity và tăng borrowCount
            book.borrowBook();

            System.out.println("Book '" + book.getTitle()
                    + "' borrowed by '" + member.getName()
                    + "' successfully.");

            return true;

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Chức năng trả sách
    public boolean returnBook(String memberID, String bookID, String returnDateText) {
        try {
            memberID = validateText(memberID, "Member ID");
            bookID = validateText(bookID, "Book ID");
            returnDateText = validateText(returnDateText, "Return date");

            Member member = findMemberByID(memberID);
            if (member == null) {
                throw new IllegalArgumentException("Fail: Member not found.");
            }

            Book book = findBookByID(bookID);
            if (book == null) {
                throw new IllegalArgumentException("Fail: Book not found.");
            }

            LocalDate returnDate = parseDate(returnDateText, "return date");

            BorrowRecord record = findActiveBorrowRecord(memberID, bookID);
            if (record == null) {
                throw new IllegalStateException("Fail: This member is not borrowing this book.");
            }

            if (returnDate.isBefore(record.getBorrowDate())) {
                throw new IllegalArgumentException("Fail: Return date cannot be before borrow date.");
            }

            record.setReturnDate(returnDate);

            // Book.java đã có sẵn returnBook(): tự tăng quantity
            book.returnBook();

            System.out.println("Book '" + book.getTitle()
                    + "' returned by '" + member.getName()
                    + "' successfully.");

            return true;

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Tìm sách theo ID
    private Book findBookByID(String bookID) {
        for (Book book : books) {
            if (book.getBookID().equalsIgnoreCase(bookID)) {
                return book;
            }
        }
        return null;
    }

    // Tìm thành viên theo ID
    private Member findMemberByID(String memberID) {
        for (Member member : members) {
            if (member.getMemberID().equalsIgnoreCase(memberID)) {
                return member;
            }
        }
        return null;
    }

    // Đếm số sách thành viên đang mượn
    private int countCurrentBorrowedBooks(String memberID) {
        int count = 0;

        for (BorrowRecord record : borrowRecords) {
            if (record.getMemberID().equalsIgnoreCase(memberID)
                    && record.getReturnDate() == null) {
                count++;
            }
        }

        return count;
    }

    // Lấy giới hạn mượn sách theo loại thành viên
    private int getBorrowLimit(Member member) {
        String className = member.getClass().getSimpleName();

        if (className.equals("PremiumMember")) {
            return 10;
        }

        if (className.equals("RegularMember")) {
            return 3;
        }

        return 3;
    }

    // Kiểm tra thành viên có đang mượn quyển sách này chưa trả hay không
    private boolean isBookAlreadyBorrowedByMember(String memberID, String bookID) {
        return findActiveBorrowRecord(memberID, bookID) != null;
    }

    // Tìm giao dịch mượn sách chưa trả
    private BorrowRecord findActiveBorrowRecord(String memberID, String bookID) {
        for (BorrowRecord record : borrowRecords) {
            if (record.getMemberID().equalsIgnoreCase(memberID)
                    && record.getBookID().equalsIgnoreCase(bookID)
                    && record.getReturnDate() == null) {
                return record;
            }
        }

        return null;
    }

    // Kiểm tra chuỗi nhập vào
    private String validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Fail: " + fieldName + " must not be empty.");
        }

        return value.trim();
    }

    // Chuyển chuỗi ngày thành LocalDate
    private LocalDate parseDate(String dateText, String fieldName) {
        try {
            return LocalDate.parse(dateText, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fail: Invalid " + fieldName + " format. Use DD/MM/YYYY.");
        }
    }

    // Tạo mã giao dịch tự động T0001, T0002, T0003,...
    private String generateTransactionID() {
        return "T" + String.format("%04d", borrowRecords.size() + 1);
    }

    public List<BorrowingTransaction> getTransactions() {
        return transactions;
    }

    // Class phụ để BorrowService tự lưu giao dịch mượn/trả
    private static class BorrowRecord {
        private final String transactionID;
        private final String bookID;
        private final String memberID;
        private final LocalDate borrowDate;
        private LocalDate returnDate;

        public BorrowRecord(String transactionID, String bookID, String memberID, LocalDate borrowDate) {
            this.transactionID = transactionID;
            this.bookID = bookID;
            this.memberID = memberID;
            this.borrowDate = borrowDate;
            this.returnDate = null;
        }

        public String getTransactionID() {
            return transactionID;
        }

        public String getBookID() {
            return bookID;
        }

        public String getMemberID() {
            return memberID;
        }

        public LocalDate getBorrowDate() {
            return borrowDate;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
        }
    }
}