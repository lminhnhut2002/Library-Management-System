package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import model.Book;
import model.BorrowingTransaction;
import model.Member;
import service.BorrowService;
import service.ReportService;

public class ReportMenu {

    private Scanner sc;
    private ReportService reportService;
    private BorrowService borrowService;

    public ReportMenu(Scanner sc, ReportService reportService, BorrowService borrowService) {
        this.sc = sc;
        this.reportService = reportService;
        this.borrowService = borrowService;
    }

    public void show() {
        int choice;

        do {
            System.out.println("\n----------- REPORTS -----------");
            System.out.println("1. Overdue Books");
            System.out.println("2. Borrowed Books");
            System.out.println("3. Most Popular Books");
            System.out.println("4. Top Borrowing Members");
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
                        displayOverdueBooksReport(reportService.getOverdueBooks());
                        break;
                    case 2:
                        displayBorrowedBooksReport(reportService.getBorrowedBooks());
                        break;
                    case 3:
                        displayPopularBooksReport(reportService.getMostPopularBooks());
                        break;
                    case 4:
                        displayTopMembers(reportService.getTopBorrowingMembers());
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

    private void displayOverdueBooksReport(ArrayList<BorrowingTransaction> transactions) {
        System.out.println("----------- OVERDUE BOOKS -----------");
        System.out.printf("%-10s %-25s %-12s %-20s %-12s %-15s%n",
                "Book ID", "Title", "Member ID", "Member Name", "Due Date", "Days Overdue");
        System.out.println("--------------------------------------------------------------------------------------------------");

        if (transactions.isEmpty()) {
            System.out.println("No overdue books found.");
        } else {
            LocalDate today = LocalDate.now();

            for (BorrowingTransaction t : transactions) {
                Book book = borrowService.findBookByID(t.getBookID());
                Member member = borrowService.findMemberByID(t.getMemberID());

                String title = (book != null) ? book.getTitle() : "Unknown";
                String memberName = (member != null) ? member.getName() : "Unknown";

                System.out.printf("%-10s %-25s %-12s %-20s %-12s %-15d%n",
                        t.getBookID(),
                        title,
                        t.getMemberID(),
                        memberName,
                        t.getDueDate(),
                        t.getOverdueDays(today));
            }
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayBorrowedBooksReport(ArrayList<BorrowingTransaction> transactions) {
        System.out.println("----------- CURRENTLY BORROWED BOOKS -----------");
        System.out.printf("%-8s %-10s %-25s %-12s %-12s%n",
                "TID", "Book ID", "Title", "Member ID", "Borrow Date");
        System.out.println("--------------------------------------------------------------------------------");

        if (transactions.isEmpty()) {
            System.out.println("No borrowed books found.");
        } else {
            for (BorrowingTransaction t : transactions) {
                Book book = borrowService.findBookByID(t.getBookID());

                String title = (book != null) ? book.getTitle() : "Unknown";

                System.out.printf("%-8s %-10s %-25s %-12s %-12s%n",
                        t.getTransactionID(),
                        t.getBookID(),
                        title,
                        t.getMemberID(),
                        t.getBorrowDate());
            }
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayPopularBooksReport(ArrayList<Book> books) {
        System.out.println("----------- MOST POPULAR BOOKS -----------");
        System.out.printf("%-10s %-30s %-15s%n",
                "Book ID", "Title", "Times Borrowed");
        System.out.println("------------------------------------------------------------");

        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : books) {
                System.out.printf("%-10s %-30s %-15d%n",
                        book.getBookID(),
                        book.getTitle(),
                        book.getBorrowCount());
            }
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayTopMembers(ArrayList<Member> members) {
        System.out.println("----------- TOP BORROWING MEMBERS -----------");
        System.out.printf("%-10s %-25s %-15s%n",
                "ID", "Name", "Borrowings");
        System.out.println("--------------------------------------------------");

        if (members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            for (Member member : members) {
                System.out.printf("%-10s %-25s %-15d%n",
                        member.getMemberID(),
                        member.getName(),
                        reportService.countBorrowingsOfMember(member.getMemberID()));
            }
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }
}
