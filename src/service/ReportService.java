/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import model.Book;
import model.Member;
import model.BorrowingTransaction;

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

    // Chức năng hiển thị sách quá hạn
    public void showOverdueBooks() {
        System.out.println("\n===== OVERDUE BOOKS LIST =====");

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
            System.out.println("No overdue books found.");
        }
    }

    // Chức năng hiển thị sách đang được mượn
    public void showBorrowedBooks() {
        System.out.println("\n===== BORROWED BOOKS LIST =====");

        boolean found = false;

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                t.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently borrowed.");
        }
    }

    // Chức năng hiển thị sách được mượn nhiều nhất
    public void showMostPopularBooks() {
        System.out.println("\n===== MOST POPULAR BOOK =====");

        if (books.isEmpty()) {
            System.out.println("The book list is empty.");
            return;
        }

        Book maxBook = books.get(0);

        for (Book b : books) {
            if (b.getBorrowCount() > maxBook.getBorrowCount()) {
                maxBook = b;
            }
        }

        if (maxBook.getBorrowCount() == 0) {
            System.out.println("No book has been borrowed yet.");
        } else {
            maxBook.displayInfo();
        }
    }

    // Chức năng hiển thị thành viên mượn sách nhiều nhất
    public void showTopBorrowingMembers() {
        System.out.println("\n===== TOP BORROWING MEMBERS =====");

        if (transactions.isEmpty()) {
            System.out.println("There are no borrowing transactions.");
            return;
        }

        HashMap<String, Integer> countMap = new HashMap<>();

        for (BorrowingTransaction t : transactions) {
            String memberID = t.getMemberID();

            if (countMap.containsKey(memberID)) {
                countMap.put(memberID, countMap.get(memberID) + 1);
            } else {
                countMap.put(memberID, 1);
            }
        }

        int max = 0;

        for (String id : countMap.keySet()) {
            if (countMap.get(id) > max) {
                max = countMap.get(id);
            }
        }

        boolean found = false;

        for (Member m : members) {
            String id = m.getMemberID();

            if (countMap.containsKey(id) && countMap.get(id) == max) {
                m.displayInfo();
                System.out.println("Number of borrowings: " + max);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No borrowing member found.");
        }
    }

    // Chức năng thống kê tổng quan
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

        System.out.println("\n===== GENERAL SUMMARY =====");
        System.out.println("Total books: " + books.size());
        System.out.println("Total members: " + members.size());
        System.out.println("Total transactions: " + transactions.size());
        System.out.println("Currently borrowed books: " + borrowingCount);
        System.out.println("Returned books: " + returnedCount);
    }
}