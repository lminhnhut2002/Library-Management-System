/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDate;
import java.util.ArrayList;
import model.Book;
import model.BorrowingTransaction;
import model.Member;

public class ReportService {

    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<BorrowingTransaction> transactions;

    public ReportService(ArrayList<Book> books,
                         ArrayList<Member> members,
                         ArrayList<BorrowingTransaction> transactions) {
        this.books = books;
        this.members = members;
        this.transactions = transactions;
    }

    // 1. Hiển thị tất cả sách đang được mượn
    public void showBorrowedBooks() {
        System.out.println("\n===== CURRENT BORROWED BOOKS =====");

        boolean found = false;

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                t.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No book is currently borrowed.");
        }
    }

    // 2. Hiển thị lịch sử mượn trả
    public void showBorrowHistory() {
        System.out.println("\n===== BORROW HISTORY =====");

        if (transactions.isEmpty()) {
            System.out.println("No transaction found.");
            return;
        }

        for (BorrowingTransaction t : transactions) {
            t.displayInfo();
        }
    }

    // 3. Tính tổng tiền phạt
    public double getTotalFine() {
        double total = 0;

        for (BorrowingTransaction t : transactions) {
            total += t.getFine();
        }

        return total;
    }

    public void showTotalFine() {
        System.out.println("\n===== TOTAL FINE =====");
        System.out.println("Total fine: " + getTotalFine() + " VND");
    }

    // 4. Tìm sách được mượn nhiều nhất
    public Book getMostBorrowedBook() {
        if (books.isEmpty()) {
            return null;
        }

        Book maxBook = books.get(0);

        for (Book b : books) {
            if (b.getBorrowCount() > maxBook.getBorrowCount()) {
                maxBook = b;
            }
        }

        return maxBook;
    }

    public void showMostBorrowedBook() {
        System.out.println("\n===== MOST BORROWED BOOK =====");

        Book b = getMostBorrowedBook();

        if (b == null || b.getBorrowCount() == 0) {
            System.out.println("No borrowed book found.");
            return;
        }

        b.displayInfo();
    }

    // 5. Hiển thị sách trả trễ
    public void showOverdueBooks() {
        System.out.println("\n===== OVERDUE BOOKS =====");

        boolean found = false;
        LocalDate today = LocalDate.now();

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()
                    && t.getDueDate() != null
                    && today.isAfter(t.getDueDate())) {

                t.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No overdue book found.");
        }
    }

    // 6. Thống kê tổng số lượng
    public void showSummary() {
        int borrowingCount = 0;
        int returnedCount = 0;

        for (BorrowingTransaction t : transactions) {
            if (t.isReturned()) {
                returnedCount++;
            } else {
                borrowingCount++;
            }
        }

        System.out.println("\n===== LIBRARY REPORT SUMMARY =====");
        System.out.println("Total books: " + books.size());
        System.out.println("Total members: " + members.size());
        System.out.println("Total transactions: " + transactions.size());
        System.out.println("Borrowing books: " + borrowingCount);
        System.out.println("Returned books: " + returnedCount);
        System.out.println("Total fine: " + getTotalFine() + " VND");
    }
}
