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
        System.out.println("\n===== DANH SACH SACH QUA HAN =====");

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
            System.out.println("Khong co sach qua han.");
        }
    }

    // Chức năng hiển thị sách đang được mượn
    public void showBorrowedBooks() {
        System.out.println("\n===== DANH SACH SACH DANG MUON =====");

        boolean found = false;

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                t.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Khong co sach dang duoc muon.");
        }
    }

    // Chức năng hiển thị sách được mượn nhiều nhất
    public void showMostPopularBooks() {
        System.out.println("\n===== SACH DUOC MUON NHIEU NHAT =====");

        if (books.isEmpty()) {
            System.out.println("Danh sach sach rong.");
            return;
        }

        Book maxBook = books.get(0);

        for (Book b : books) {
            if (b.getBorrowCount() > maxBook.getBorrowCount()) {
                maxBook = b;
            }
        }

        if (maxBook.getBorrowCount() == 0) {
            System.out.println("Chua co sach nao duoc muon.");
        } else {
            maxBook.displayInfo();
        }
    }

    // Chức năng hiển thị thành viên mượn sách nhiều nhất
    public void showTopBorrowingMembers() {
        System.out.println("\n===== THANH VIEN MUON SACH NHIEU NHAT =====");

        if (transactions.isEmpty()) {
            System.out.println("Chua co giao dich muon sach.");
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
                System.out.println("So lan muon: " + max);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Khong tim thay thanh vien muon sach.");
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

        System.out.println("\n===== THONG KE TONG QUAN =====");
        System.out.println("Tong so sach: " + books.size());
        System.out.println("Tong so thanh vien: " + members.size());
        System.out.println("Tong so giao dich: " + transactions.size());
        System.out.println("So sach dang muon: " + borrowingCount);
        System.out.println("So sach da tra: " + returnedCount);
    }
}