package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import model.BorrowingTransaction;
import model.Book;
import model.Member;
import service.BorrowService;
import utils.DateUtils;
import utils.Validation;

public class BorrowMenu {

    private Scanner sc;
    private BorrowService borrowService;

    public BorrowMenu(Scanner sc, BorrowService borrowService) {
        this.sc = sc;
        this.borrowService = borrowService;
    }

    public void show() {
        int choice;

        do {
            System.out.println("\n----------- BORROWING / RETURNING -----------");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. View Borrowed Books");
            System.out.println("4. Borrowing History");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            try {
                switch (choice) {
                    case 1:
                        borrowBook();
                        break;
                    case 2:
                        returnBook();
                        break;
                    case 3:
                        displayTransactions(borrowService.getCurrentBorrowedBooks());
                        break;
                    case 4:
                        borrowingHistory();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Fail: " + e.getMessage());
            }

        } while (choice != 5);
    }

    private void borrowBook() throws Exception {
        System.out.println("----------- BORROW BOOK -----------");
        String memberID = Validation.inputRequired(sc, "MemberID: ", "Member ID").toUpperCase();
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Member ID Invalid.");
        }
        String bookID = Validation.inputRequired(sc, "BookID: ", "Book ID").toUpperCase();
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Book ID Invalid.");
        }

        LocalDate borrowDate = DateUtils.parseDate(Validation.inputRequired(sc,
                "Borrow Date (DD/MM/YYYY): ",
                "Borrow Date"));
        System.out.print("[1] Confirm [2] Cancel: ");
        String confirm = sc.nextLine();

        if (confirm.equals("1")) {
            // Lấy thông tin trước để hiển thị theo đúng format của đề bài
            Book book = borrowService.findByBookID(bookID);
            Member member = borrowService.findByMemberID(memberID);

            borrowService.borrowBook(memberID, bookID, borrowDate);

            System.out.printf("Output: Book '%s' borrowed by '%s' successfully.\n",
                    book.getTitle(), member.getName());
        } else {
            System.out.println("Cancelled.");
        }
    }

    private void returnBook() throws Exception {
        System.out.println("----------- RETURN BOOK -----------");
        String memberID = Validation.inputRequired(sc, "MemberID: ", "Member ID").toUpperCase();
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Member ID Invalid.");
        }
        String bookID = Validation.inputRequired(sc, "BookID: ", "Book ID").toUpperCase();
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Book ID Invalid.");
        }

        LocalDate returnDate = DateUtils.parseDate(Validation.inputRequired(sc,
                "Return Date (DD/MM/YYYY): ",
                "Return Date"));

        System.out.print("[1] Confirm [2] Cancel: ");
        String confirm = sc.nextLine();

        if (confirm.equals("1")) {
            Book book = borrowService.findByBookID(bookID);
            Member member = borrowService.findByMemberID(memberID);

            double fine = borrowService.returnBook(memberID, bookID, returnDate);

            if (fine > 0) {
                System.out.printf("Output: Book '%s' returned by '%s'. Overdue fine: %.0f VND.\n",
                        book.getTitle(), member.getName(), fine);
            } else {
                System.out.printf("Output: Book '%s' returned by '%s'. No overdue fine.\n",
                        book.getTitle(), member.getName());
            }
        } else {
            System.out.println("Cancelled.");
        }
    }

    public void displayTransactions(ArrayList<BorrowingTransaction> transactions) {
        System.out.println("----------- TRANSACTION LIST -----------");
        System.out.printf("%-8s %-8s %-8s %-12s %-12s %-15s %-10s %-8s\n",
                "TID", "BookID", "MemID", "Borrow", "Due", "Return", "Fine", "Returned");
        System.out.println("------------------------------------------------------------------------------------------");

        for (BorrowingTransaction t : transactions) {
            t.displayTransaction();
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void borrowingHistory() throws Exception {
        String memberID = Validation.inputRequired(sc, "MemberID: ", "Member ID").toUpperCase();
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Member ID Invalid.");
        }

        try {
            displayTransactions(borrowService.getBorrowingHistory(memberID));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayBookHeader() {
        System.out.printf("%-6s %-25s %-22s %-17s %-6s %-4s\n",
                "ID", "Title", "Author", "Genre", "PublicYear", "Quantity");
        System.out.println("-----------------------------------------------------------------------");
    }
}
