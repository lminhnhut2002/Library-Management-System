/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import model.Book;
import service.BookService;
import utils.Validation;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author NHUT
 */
public class BookMenu {
    private final BookService bookService;
    private final Scanner scanner; 

    public BookMenu(BookService bookService) {
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== 1. BOOK MANAGEMENT ===");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. View All Books");
            System.out.println("5. Search Book");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        addBookForm();
                        break;
                    case 2:
                        updateBookForm();
                        break;
                    case 3:
                        removeBookForm();
                        break;
                    case 4:
                        viewAllBooksForm();
                        break;
                    case 5:
                        searchBookForm();
                        break;
                    case 0:
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please choose between 0 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Invalid Input. Please enter a number.");
                choice = -1;
            }
        } while (choice != 0);
    }

    // 1. Chức năng Add Book
    private void addBookForm() {
        System.out.println("\n--- Add Book ---");
        
        System.out.print("Book ID (e.g., BK123456): ");
        String bookID = scanner.nextLine().trim();
        if (!Validation.checkBookID(bookID)) { 
            System.out.println("❌ Fail: Invalid Book ID. Must start with 'BK' followed by 6-8 digits.");
            return;
        }

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        if (Validation.isEmpty(title)) {
            System.out.println("❌ Fail: Title cannot be empty.");
            return;
        }
        
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        if (Validation.isEmpty(author)) {
            System.out.println("❌ Fail: Author cannot be empty.");
            return;
        }
        
        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();
        if (Validation.isEmpty(genre)) {
            System.out.println("❌ Fail: Genre cannot be empty.");
            return;
        }
        
        System.out.print("Publication Year: ");
        String yearStr = scanner.nextLine().trim();
        
        System.out.print("New Quantity: ");
        String quantityStr = scanner.nextLine().trim();

        try {
            int year = Integer.parseInt(yearStr);
            int quantity = Integer.parseInt(quantityStr);

            // Tạo đối tượng Book từ thông tin nhập vào
            Book newBook = new Book(bookID, title, author, genre, year, quantity);
            
            // ĐÃ SỬA: Bọc lệnh này vào trong khối try-catch để xử lý "throws Exception" của hàm addBook gốc
            bookService.addBook(newBook); 
            System.out.println("🎉 Book added successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Fail: Year and Quantity must be valid integers.");
        } catch (Exception e) {
            // Bắt và hiển thị thông báo lỗi (ví dụ: trùng ID, sai năm...) được ném từ BookService
            System.out.println("❌ Fail: " + e.getMessage()); 
        }
    }

    // 2. Chức năng Update Book
    private void updateBookForm() {
        System.out.println("\n--- Update Book ---");
        System.out.print("Enter Book ID (need update): ");
        String bookID = scanner.nextLine().trim();

        if (!Validation.checkBookID(bookID)) {
            System.out.println("❌ Fail: Invalid Book ID format.");
            return;
        }

        // Gọi hàm findByID gốc của bạn để kiểm tra xem sách có tồn tại không
        Book existingBook = bookService.findByID(bookID); 
        if (existingBook == null) {
            System.out.println("❌ Error: Book Not Found.");
            return;
        }

        //  Nhập đầy đủ thông tin gồm cả Author và Quantity để truyền cho hàm updateBook 6 tham số
        System.out.print("New Title: ");
        String newTitle = scanner.nextLine().trim();
        
        System.out.print("New Author: ");
        String newAuthor = scanner.nextLine().trim();
        
        System.out.print("New Genre: ");
        String newGenre = scanner.nextLine().trim();
        
        System.out.print("New Publication Year: ");
        String newYearStr = scanner.nextLine().trim();
        
        System.out.print("New Quantity: ");
        String newQuantityStr = scanner.nextLine().trim();

        try {
            int newYear = Integer.parseInt(newYearStr);
            int newQuantity = Integer.parseInt(newQuantityStr);
            
            //  Truyền đủ 6 tham số và bọc trong try-catch
            bookService.updateBook(bookID, newTitle, newAuthor, newGenre, newYear, newQuantity);
            System.out.println("🎉 Book updated successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Fail: Year and Quantity must be valid integers.");
        } catch (Exception e) {
            System.out.println("❌ Fail: " + e.getMessage());
        }
    }

    // 3. Chức năng Remove Book
    private void removeBookForm() {
        System.out.println("\n--- Remove Book ---");
        System.out.print("Enter Book ID (Sách cần xóa): ");
        String bookID = scanner.nextLine().trim();

        if (!Validation.checkBookID(bookID)) {
            System.out.println("❌ Fail: Invalid Book ID format.");
            return;
        }

        try {
            // Gọi hàm removeBook(bookID) gốc của bạn và xử lý Exception nếu không tìm thấy sách
            bookService.removeBook(bookID); 
            System.out.println("🎉 Book removed successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage()); 
        }
    }

    // 4. Chức năng View All Books
    private void viewAllBooksForm() {
        System.out.println("\n--- View All Books ---");
        // Gọi hàm getBooks() trả về ArrayList<Book> 
        ArrayList<Book> books = bookService.getBooks(); 

        if (books == null || books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        System.out.printf("%-10s %-30s %-20s %-15s %-6s %-8s\n", 
                "ID", "Title", "Author", "Genre", "Year", "Stock");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        for (Book book : books) {
            book.displayInfo(); 
        }
    }

    // 5. Chức năng Search Book
    private void searchBookForm() {
        System.out.println("\n--- Search Book ---");
        System.out.print("Enter title / author / genre: ");
        String keyword = scanner.nextLine().trim();

        if (Validation.isEmpty(keyword)) {
            System.out.println("❌ Fail: Keyword cannot be empty.");
            return;
        }

        // Gọi chính xác hàm searchBook(keyword) trả về ArrayList<Book> 
        ArrayList<Book> foundBooks = bookService.searchBook(keyword);

        if (foundBooks == null || foundBooks.isEmpty()) {
            System.out.println("❌ No books found matching the keyword.");
            return;
        }

        System.out.println("\n🔍 Found " + foundBooks.size() + " book(s):");
        System.out.printf("%-10s %-30s %-20s %-15s %-6s %-8s\n", 
                "ID", "Title", "Author", "Genre", "Year", "Stock");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        for (Book book : foundBooks) {
            book.displayInfo();
        }
    }
}

