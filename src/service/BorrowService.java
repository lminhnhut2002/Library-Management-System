/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.BorrowingTransaction;
import model.Member;

/**
 * Service xử lý chức năng mượn/trả sách.
 *
 * Commit tiếng Việt:
 * - Viết lại BorrowService theo yêu cầu Borrowing / Returning.
 * - Kiểm tra member tồn tại trước khi mượn sách.
 * - Kiểm tra sách tồn tại và còn số lượng trước khi mượn.
 * - Kiểm tra giới hạn mượn sách của từng loại member.
 * - Giảm số lượng sách khi mượn và tăng lại khi trả.
 * - Tính tiền phạt nếu trả sách quá hạn.
 * - Thêm chức năng xem sách đang mượn và lịch sử mượn theo member.
 *
 * @author PC
 */
public class BorrowService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Theo ví dụ trong đề: 28/04/2026 -> 05/05/2026 là không phạt, tức hạn mượn 7 ngày
    private static final int DEFAULT_BORROW_DAYS = 7;

    private final List<Book> books;
    private final List<Member> members;
    private final List<BorrowingTransaction> transactions;

    public BorrowService(List<Book> books, List<Member> members, List<BorrowingTransaction> transactions) {
        this.books = books == null ? new ArrayList<Book>() : books;
        this.members = members == null ? new ArrayList<Member>() : members;
        this.transactions = transactions == null ? new ArrayList<BorrowingTransaction>() : transactions;
    }

    // Mượn sách theo ngày hiện tại
    public boolean borrowBook(String memberID, String bookID) {
        return borrowBook(memberID, bookID, LocalDate.now().format(FORMATTER));
    }

    // Mượn sách theo ngày nhập vào, định dạng DD/MM/YYYY
    public boolean borrowBook(String memberID, String bookID, String borrowDateText) {
        try {
            memberID = validateRequiredText(memberID, "Member ID");
            bookID = validateRequiredText(bookID, "Book ID");
            borrowDateText = validateRequiredText(borrowDateText, "Borrow date");

            Member member = findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }

            Book book = findBookByID(bookID);
            if (book == null) {
                throw new Exception("Book not found.");
            }

            LocalDate borrowDate = parseDate(borrowDateText, "borrow date");

            if (borrowDate.isAfter(LocalDate.now())) {
                throw new Exception("Borrow date cannot be in the future.");
            }

            if (!book.isAvailable()) {
                throw new Exception("Book is out of stock.");
            }

            if (countActiveBorrowedBooks(memberID) >= member.getBorrowLimit()) {
                throw new Exception("Borrow limit exceeded. This member can borrow maximum "
                        + member.getBorrowLimit() + " book(s).");
            }

            if (findActiveTransaction(memberID, bookID) != null) {
                throw new Exception("This member has already borrowed this book and has not returned it yet.");
            }

            String transactionID = generateTransactionID();
            LocalDate dueDate = borrowDate.plusDays(DEFAULT_BORROW_DAYS);

            BorrowingTransaction transaction = new BorrowingTransaction(
                    transactionID,
                    bookID,
                    memberID,
                    borrowDate,
                    dueDate,
                    null,
                    0,
                    false
            );

            transactions.add(transaction);

            // Giảm stock và tăng borrowCount
            book.borrowBook();

            System.out.println("Book '" + book.getTitle() + "' borrowed by '" + member.getName() + "' successfully.");
            System.out.println("Due date: " + dueDate.format(FORMATTER));

            return true;

        } catch (Exception e) {
            System.out.println("Borrow failed: " + e.getMessage());
            return false;
        }
    }

    // Trả sách theo ngày hiện tại
    public boolean returnBook(String memberID, String bookID) {
        return returnBook(memberID, bookID, LocalDate.now().format(FORMATTER));
    }

    // Trả sách theo ngày nhập vào, định dạng DD/MM/YYYY
    public boolean returnBook(String memberID, String bookID, String returnDateText) {
        try {
            memberID = validateRequiredText(memberID, "Member ID");
            bookID = validateRequiredText(bookID, "Book ID");
            returnDateText = validateRequiredText(returnDateText, "Return date");

            Member member = findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }

            Book book = findBookByID(bookID);
            if (book == null) {
                throw new Exception("Book not found.");
            }

            BorrowingTransaction transaction = findActiveTransaction(memberID, bookID);
            if (transaction == null) {
                throw new Exception("No active borrowing transaction found for this member and book.");
            }

            LocalDate returnDate = parseDate(returnDateText, "return date");

            if (returnDate.isBefore(transaction.getBorrowDate())) {
                throw new Exception("Return date must be after borrow date.");
            }

            int overdueDays = transaction.getOverdueDays(returnDate);
            double fine = member.calculateFine(overdueDays);

            transaction.markReturned(returnDate, fine);

            // Tăng lại stock
            book.returnBook();

            if (fine > 0) {
                System.out.printf("Book '%s' returned by '%s'. Overdue fine: %.0f VND.\n",
                        book.getTitle(), member.getName(), fine);
            } else {
                System.out.println("Book '" + book.getTitle() + "' returned by '" + member.getName()
                        + "'. No overdue fine.");
            }

            return true;

        } catch (Exception e) {
            System.out.println("Return failed: " + e.getMessage());
            return false;
        }
    }

    // Xem tất cả sách đang được mượn, tức là chưa trả
    public void viewBorrowedBooks() {
        boolean found = false;

        System.out.println("----------- BORROWED BOOKS -----------");
        System.out.printf("%-8s %-8s %-20s %-8s %-20s %-12s %-12s\n",
                "Book ID", "Mem ID", "Book Title", "Member", "Member Name", "Borrow Date", "Due Date");
        System.out.println("--------------------------------------------------------------------------------");

        for (BorrowingTransaction transaction : transactions) {
            if (!transaction.isReturned()) {
                Book book = findBookByID(transaction.getBookID());
                Member member = findMemberByID(transaction.getMemberID());

                String title = book == null ? "Unknown" : book.getTitle();
                String memberName = member == null ? "Unknown" : member.getName();

                System.out.printf("%-8s %-8s %-20s %-8s %-20s %-12s %-12s\n",
                        transaction.getBookID(),
                        transaction.getMemberID(),
                        title,
                        transaction.getMemberID(),
                        memberName,
                        transaction.getBorrowDate().format(FORMATTER),
                        transaction.getDueDate().format(FORMATTER));

                found = true;
            }
        }

        if (!found) {
            System.out.println("No borrowed books found.");
        }
    }

    // Xem lịch sử mượn sách của một member
    public void viewBorrowingHistory(String memberID) {
        try {
            memberID = validateRequiredText(memberID, "Member ID");

            Member member = findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }

            boolean found = false;

            System.out.println("----------- BORROWING HISTORY -----------");
            System.out.println("Member ID: " + member.getMemberID());
            System.out.println("Member Name: " + member.getName());

            System.out.printf("%-8s %-8s %-20s %-12s %-12s %-12s %-10s %-8s\n",
                    "Tran ID", "Book ID", "Title", "Borrow", "Due", "Return", "Fine", "Status");
            System.out.println("--------------------------------------------------------------------------------");

            for (BorrowingTransaction transaction : transactions) {
                if (transaction.getMemberID().equalsIgnoreCase(memberID)) {
                    Book book = findBookByID(transaction.getBookID());
                    String title = book == null ? "Unknown" : book.getTitle();

                    String returnDate = transaction.getReturnDate() == null
                            ? "N/A"
                            : transaction.getReturnDate().format(FORMATTER);

                    String status = transaction.isReturned() ? "Returned" : "Borrowed";

                    System.out.printf("%-8s %-8s %-20s %-12s %-12s %-12s %-10.0f %-8s\n",
                            transaction.getTranscationID(),
                            transaction.getBookID(),
                            title,
                            transaction.getBorrowDate().format(FORMATTER),
                            transaction.getDueDate().format(FORMATTER),
                            returnDate,
                            transaction.getFine(),
                            status);

                    found = true;
                }
            }

            if (!found) {
                System.out.println("This member has no borrowing history.");
            }

        } catch (Exception e) {
            System.out.println("View borrowing history failed: " + e.getMessage());
        }
    }

    // Hiển thị tất cả giao dịch mượn/trả
    public void displayAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No borrowing transaction found.");
            return;
        }

        System.out.printf("%-8s %-8s %-8s %-12s %-12s %-15s %-10s %-8s\n",
                "ID", "Book", "Member", "Borrow", "Due", "Return", "Fine", "Returned");

        for (BorrowingTransaction transaction : transactions) {
            transaction.displayInfo();
        }
    }

    // Tìm sách theo mã sách
    private Book findBookByID(String bookID) {
        for (Book book : books) {
            if (book.getBookID().equalsIgnoreCase(bookID)) {
                return book;
            }
        }
        return null;
    }

    // Tìm thành viên theo mã thành viên
    private Member findMemberByID(String memberID) {
        for (Member member : members) {
            if (member.getMemberID().equalsIgnoreCase(memberID)) {
                return member;
            }
        }
        return null;
    }

    // Tìm giao dịch đang mượn, tức là chưa trả sách
    private BorrowingTransaction findActiveTransaction(String memberID, String bookID) {
        for (BorrowingTransaction transaction : transactions) {
            if (transaction.getMemberID().equalsIgnoreCase(memberID)
                    && transaction.getBookID().equalsIgnoreCase(bookID)
                    && !transaction.isReturned()) {
                return transaction;
            }
        }
        return null;
    }

    // Đếm số sách member đang mượn chưa trả
    private int countActiveBorrowedBooks(String memberID) {
        int count = 0;

        for (BorrowingTransaction transaction : transactions) {
            if (transaction.getMemberID().equalsIgnoreCase(memberID)
                    && !transaction.isReturned()) {
                count++;
            }
        }

        return count;
    }

    // Tạo mã giao dịch tự động: T0001, T0002, T0003,...
    private String generateTransactionID() {
        int maxNumber = 0;

        for (BorrowingTransaction transaction : transactions) {
            String id = transaction.getTranscationID();

            if (id != null && id.matches("T\\d+")) {
                int number = Integer.parseInt(id.substring(1));

                if (number > maxNumber) {
                    maxNumber = number;
                }
            }
        }

        return "T" + String.format("%04d", maxNumber + 1);
    }

    // Kiểm tra input không được rỗng
    private String validateRequiredText(String value, String fieldName) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            throw new Exception(fieldName + " cannot be empty.");
        }

        return value.trim();
    }

    // Chuyển String sang LocalDate theo định dạng DD/MM/YYYY
    private LocalDate parseDate(String dateText, String fieldName) throws Exception {
        try {
            return LocalDate.parse(dateText, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new Exception("Invalid " + fieldName + " format. Use DD/MM/YYYY.");
        }
    }

    public List<BorrowingTransaction> getTransactions() {
        return transactions;
    }
}