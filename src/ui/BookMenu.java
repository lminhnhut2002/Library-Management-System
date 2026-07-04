package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Book;
import model.BorrowingTransaction;
import service.BookService;
import service.BorrowService;
import utils.Validation;

public class BookMenu {

    private Scanner sc;
    private BookService bookService;
    private BorrowService borrowService;

    public BookMenu(Scanner sc, BookService bookService, BorrowService borrowService) {
        this.sc = sc;
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    public void show() {
        int choice;

        do {
            System.out.println("\n----------- MANAGE BOOKS -----------");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. View All Books");
            System.out.println("5. Search Book");
            System.out.println("6. Back");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            try {
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        updateBook();
                        break;
                    case 3:
                        removeBook();
                        break;
                    case 4:
                        displayBooks(bookService.getBooks());
                        break;
                    case 5:
                        searchBook();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Fail: " + e.getMessage());
            }

        } while (choice != 6);
    }

    private void addBook() throws Exception {
        System.out.println("----------- ADD BOOK -----------");

        String bookID = Validation.inputRequired(sc, "Book ID: ", "Book ID").toUpperCase();
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Book ID Invalid.");
        }
        String title = Validation.inputRequired(sc, "Title: ", "Title");
        String author = Validation.inputRequired(sc, "Author: ", "Author");
        String genre = Validation.inputRequired(sc, "Genre: ", "Genre");

        String yearInput = Validation.inputRequired(sc, "Publication Year: ", "Publication Year");
        int year = Integer.parseInt(yearInput);
        String quantityInput = Validation.inputRequired(sc, "Quantity: ", "Quantity");
        int quantity = Integer.parseInt(quantityInput);

        System.out.print("[1] Save [2] Cancel: ");
        String confirm = sc.nextLine();

        if (confirm.equals("1")) {
            bookService.addBook(new Book(bookID, title, author, genre, year, quantity));
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private void updateBook() throws Exception {
        System.out.println("----------- UPDATE BOOK -----------");

        String bookID = Validation.inputRequired(sc, "Book ID: ", "Book ID");
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Book ID Invalid.");
        }

        Book b = bookService.findByID(bookID);

        if (b == null) {
            throw new Exception("Book not found.");
        }

        System.out.println("Current Information:");
        displayHeader();
        b.displayInfo();

        System.out.println("(Leave blank and press ENTER to skip updating that field)");

        System.out.print("New Title: ");
        String title = sc.nextLine();

        System.out.print("New Author: ");
        String author = sc.nextLine();

        System.out.print("New Genre: ");
        String genre = sc.nextLine();

        System.out.print("New Publication Year: ");
        String yearInput = sc.nextLine();
        int year = yearInput.trim().isEmpty() ? -1 : Integer.parseInt(yearInput);

        System.out.print("New Quantity: ");
        String qtyInput = sc.nextLine();
        int quantity = qtyInput.trim().isEmpty() ? -1 : Integer.parseInt(qtyInput);

        System.out.print("[1] Update [2] Cancel: ");
        String confirm = sc.nextLine();

        if (confirm.equals("1")) {
            bookService.updateBook(bookID, title, author, genre, year, quantity);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private void removeBook() throws Exception {
        System.out.println("----------- REMOVE BOOK -----------");

        String bookID = Validation.inputRequired(sc, "Book ID: ", "Book ID");
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Book ID Invalid.");
        }

        Book b = bookService.findByID(bookID);

        if (b == null) {
            throw new Exception("Book not found.");
        }

        for (BorrowingTransaction t : borrowService.getCurrentBorrowedBooks()) {
            if (t.getBookID().equalsIgnoreCase(bookID)) {
                throw new Exception("Cannot remove. Book is currently borrowed.");
            }
        }

        bookService.removeBook(bookID);
        System.out.println("Book removed successfully.");
    }

    private void searchBook() {
        try {

            String keyword = Validation.inputRequired(sc, "Enter title / author / genre: ", "Key word");
            displayBooks(bookService.searchBooks(keyword));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayBooks(ArrayList<Book> books) {
        System.out.println("----------- BOOK LIST -----------");
        displayHeader();

        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book b : books) {
                b.displayInfo();
            }
        }
        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayHeader() {
        System.out.printf("%-6s %-25s %-22s %-12s %-6s %-4s%n",
                "ID", "Title", "Author", "Genre", "Year", "Qty");
        System.out.println("--------------------------------------------------------------------------------");
    }
}
