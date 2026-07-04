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
            System.out.println("5. Extend the due date");
            System.out.println("6. Cancel Book Borrowing");
            System.out.println("7. Books Near Due Date");
            System.out.println("8. Return All Borrowed Books");
            System.out.println("9. View All Transaction");
            System.out.println("10. Back");
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
                        viewBorrowedBooks();
                        break;
                    case 4:
                        borrowingHistory();
                        break;
                    case 5:
                        extendDueDate();
                        break;
                    case 6:
                        cancelBorrowing();
                        break;
                    case 7:
                        getBooksNearDueDate();
                        break;
                    case 8:
                        returnAllBorrowedBooks();
                        break;
                    case 9:
                        viewAllTransactions();
                        break;
                    case 10:
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Fail: " + e.getMessage());
            }

        } while (choice != 10);
    }

    // case 1
    private void borrowBook() {
        try {
            System.out.println("----------- BORROW BOOK -----------");
            String memberID = Validation.inputRequired(sc, "MemberID: ", "Member ID").toUpperCase();
            if (!Validation.isValidID(memberID)) {
                throw new Exception("Member ID Invalid.");
            }

            Member member = borrowService.findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }
            String bookID = Validation.inputRequired(sc, "BookID: ", "Book ID").toUpperCase();
            if (!Validation.checkBookID(bookID)) {
                throw new Exception("Book ID Invalid.");
            }
            Book book = borrowService.findBookByID(bookID);
            if (book == null) {
                throw new Exception("Book not found.");
            }
            if (!book.isAvailable()) {
                throw new Exception("Out of stock.");
            }
            LocalDate borrowDate = DateUtils.parseDate(Validation.inputRequired(sc,
                    "Borrow Date (DD/MM/YYYY): ",
                    "Borrow Date"));
            System.out.print("[1] Confirm [2] Cancel: ");
            String confirm = sc.nextLine();

            if (confirm.equals("1")) {

                borrowService.borrowBook(memberID, bookID, borrowDate);

                System.out.printf("Output: Book '%s' borrowed by '%s' successfully.\n",
                        book.getTitle(), member.getName());
            } else {
                System.out.println("Cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    // case 2
    private void returnBook() {
        System.out.println("----------- RETURN BOOK -----------");
        try {
            String memberID = Validation.inputRequired(sc, "MemberID: ", "Member ID").toUpperCase();
            if (!Validation.isValidID(memberID)) {
                throw new Exception("Member ID Invalid.");
            }

            Member member = borrowService.findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }
            String bookID = Validation.inputRequired(sc, "BookID: ", "Book ID").toUpperCase();
            if (!Validation.checkBookID(bookID)) {
                throw new Exception("Book ID Invalid.");
            }
            Book book = borrowService.findBookByID(bookID);
            if (book == null) {
                throw new Exception("Book not found.");
            }

            LocalDate returnDate = DateUtils.parseDate(Validation.inputRequired(sc,
                    "Return Date (DD/MM/YYYY): ",
                    "Return Date"));

            System.out.print("[1] Confirm [2] Cancel: ");
            String confirm = sc.nextLine();

            if (confirm.equals("1")) {

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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    //case 3
    public void viewBorrowedBooks() {

        ArrayList<BorrowingTransaction> result = borrowService.getCurrentBorrowedBooks();

        if (result.isEmpty()) {
            System.out.println("No borrowed books found.");
        } else {
            displayTransactionHeader();
            for (BorrowingTransaction t : result) {
                t.displayTransaction();
            }
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    //case 4
    private void borrowingHistory() {

        try {
            String memberID = Validation.inputRequired(sc, "MemberID: ", "Member ID").toUpperCase();
            if (!Validation.isValidID(memberID)) {
                throw new Exception("Member ID Invalid.");
            }
            Member member = borrowService.findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }
            ArrayList<BorrowingTransaction> result = borrowService.getBorrowingHistory(memberID);
            displayTransactionHeader();
            for (BorrowingTransaction t : result) {
                t.displayTransaction();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    // case 5
    private void extendDueDate() {

        try {
            String memberID = Validation.inputRequired(sc, "Member ID: ", "Member ID ");
            if (!Validation.isValidID(memberID)) {
                throw new Exception("Member ID Invalid.");
            }
            Member member = borrowService.findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }
            String bookID = Validation.inputRequired(sc, "Book ID: ", "Book ID ");
            if (!Validation.checkBookID(bookID)) {
                throw new Exception("Book ID Invalid.");
            }
            Book book = borrowService.findBookByID(bookID);
            if (book == null) {
                throw new Exception("Book not found.");
            }
            int extraDays = Integer.parseInt(Validation.inputRequired(sc, "Extra Day: ", "Extra day "));
            LocalDate newDueDate = borrowService.extendDueDate(memberID, bookID, extraDays);
            System.out.println("Extend due date successfully.");
            System.out.println("New due date: " + DateUtils.formatDate(newDueDate));
            double fee = borrowService.getFeeExtendDueDate(memberID, extraDays);
            System.out.println("Extension fee: " + fee + " VND");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    //case 6
    private void cancelBorrowing() {
        try {
            String memberID = Validation.inputRequired(sc, "Member ID: ", "Member ID ");
            if (!Validation.isValidID(memberID)) {
                throw new Exception("Member ID Invalid.");
            }
            Member member = borrowService.findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }
            String bookID = Validation.inputRequired(sc, "Book ID: ", "Book ID ");
            if (!Validation.checkBookID(bookID)) {
                throw new Exception("Book ID Invalid.");
            }
            Book book = borrowService.findBookByID(bookID);
            if (book == null) {
                throw new Exception("Book not found.");
            }
            borrowService.cancelBorrowing(memberID, bookID);
            System.out.println("Cancel successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    //case 7
    private void getBooksNearDueDate() {
        try {
            ArrayList<BorrowingTransaction> result = borrowService.getBooksNearDueDate();
            displayTransactionHeader();
            for (BorrowingTransaction t : result) {
                t.displayTransaction();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    //case 8
    private void returnAllBorrowedBooks() {
        try {
            String memberID = Validation.inputRequired(sc, "Member ID: ", "Member ID ");
            if (!Validation.isValidID(memberID)) {
                throw new Exception("Member ID Invalid.");
            }
            Member member = borrowService.findMemberByID(memberID);
            if (member == null) {
                throw new Exception("Member not found.");
            }
            LocalDate returnDate = DateUtils.parseDate(Validation.inputRequired(sc, "Return Date (DD/MM/YYYY): ", "Return Date "));
            ArrayList<BorrowingTransaction> listBooks = borrowService.returnAllBorrowedBooks(memberID, returnDate);
            displayTransactionHeader();
            for (BorrowingTransaction t : listBooks) {

                t.displayTransaction();
            }
            System.out.println("Return all borrowed books successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    //case 9
    private void viewAllTransactions() {
        try {
            ArrayList<BorrowingTransaction> result = borrowService.getAllTransactions();
            displayTransactionHeader();
            for (BorrowingTransaction t : result) {
                t.displayTransaction();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    public void displayTransactionHeader() {
        System.out.println("----------- TRANSACTION LIST -----------");
        System.out.printf("%-8s %-8s %-8s %-12s %-12s %-15s %-10s %-8s\n",
                "TID", "BookID", "MemID", "Borrow", "Due", "Return", "Fine", "Returned");
        System.out.println("------------------------------------------------------------------------------------------");
    }

}
