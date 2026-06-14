package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Book;
import model.Member;
import model.BorrowingTransaction;
import service.BookService;
import service.MemberService;
import service.BorrowService;
import service.ReportService;
import utils.FileUtils;

public class MainMenu {

    private Scanner sc = new Scanner(System.in);

    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<BorrowingTransaction> transactions;

    private BookService bookService;
    private MemberService memberService;
    private BorrowService borrowService;
    private ReportService reportService;

    public MainMenu() {
        books = FileUtils.loadBooks();
        members = FileUtils.loadMembers();
        transactions = FileUtils.loadTransactions();

        bookService = new BookService(books);
        memberService = new MemberService(members);
        borrowService = new BorrowService(books, members, transactions);
        reportService = new ReportService(books, members, transactions);
    }

    public void show() {
        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("LIBRARY MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("3. Borrowing/Returning");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("--------------------------------------");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    new BookMenu(sc, bookService, borrowService).show();
                    saveData();
                    break;
                case 2:
                    new MemberMenu(sc, memberService, borrowService).show();
                    saveData();
                    break;
                case 3:
                    new BorrowMenu(sc, borrowService).show();
                    saveData();
                    break;
                case 4:
                    new ReportMenu(sc, reportService, borrowService).show(); // Thêm borrowService vào đây
                    break;
                case 5:
                    saveData();
                    System.out.println("Data saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 5);
    }

    private void saveData() {
        try {
            FileUtils.saveBooks(books);
            FileUtils.saveMembers(members);
            FileUtils.saveTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Cannot save data.");
        }
    }
}
