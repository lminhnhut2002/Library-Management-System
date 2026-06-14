package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Book;
import model.Member;
import model.BorrowingTransaction;
import service.ReportService;
import service.BorrowService;
import java.time.LocalDate;

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

        } while (choice != 5);
    }

    private void displayOverdueBooksReport(ArrayList<BorrowingTransaction> transactions) {
        System.out.println("----------- OVERDUE BOOKS -----------");
        System.out.printf("%-10s %-25s %-12s %-20s %-12s %-15s\n",
                "Book ID", "Title", "Member ID", "Member Name", "Due Date", "Days Overdue");
        System.out.println("--------------------------------------------------------------------------------------------------");

        LocalDate today = LocalDate.now();
        for (BorrowingTransaction t : transactions) {
            Book b = borrowService.findByBookID(t.getBookID());
            Member m = borrowService.findByMemberID(t.getMemberID());
            
            String title = (b != null) ? b.getTitle() : "Unknown";
            String memName = (m != null) ? m.getName() : "Unknown";
            int daysOverdue = t.getOverdueDays(today);

            System.out.printf("%-10s %-25s %-12s %-20s %-12s %-15d\n",
                    t.getBookID(), title, t.getMemberID(), memName, t.getDueDate(), daysOverdue);
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayBorrowedBooksReport(ArrayList<BorrowingTransaction> transactions) {
        System.out.println("----------- CURRENTLY BORROWED BOOKS -----------");
        System.out.printf("%-8s %-10s %-25s %-12s %-12s\n", "TID", "Book ID", "Title", "Member ID", "Borrow Date");
        System.out.println("--------------------------------------------------------------------------------");
        for (BorrowingTransaction t : transactions) {
            Book b = borrowService.findByBookID(t.getBookID());
            String title = (b != null) ? b.getTitle() : "Unknown";
            System.out.printf("%-8s %-10s %-25s %-12s %-12s\n",
                    t.getTransactionID(), t.getBookID(), title, t.getMemberID(), t.getBorrowDate());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayPopularBooksReport(ArrayList<Book> books) {
        System.out.println("----------- MOST POPULAR BOOKS -----------");
        System.out.printf("%-10s %-30s %-15s\n", "Book ID", "Title", "Times Borrowed");
        System.out.println("------------------------------------------------------------");

        for (Book b : books) {
            System.out.printf("%-10s %-30s %-15d\n", b.getBookID(), b.getTitle(), b.getBorrowCount());
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayTopMembers(ArrayList<Member> members) {
        System.out.println("----------- TOP BORROWING MEMBERS -----------");
        System.out.printf("%-10s %-25s %-15s\n", "ID", "Name", "Borrowings");
        System.out.println("--------------------------------------------------");

        for (Member m : members) {
            System.out.printf("%-10s %-25s %-15d\n",
                    m.getMemberID(), m.getName(), reportService.countBorrowingsOfMember(m.getMemberID()));
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }
}