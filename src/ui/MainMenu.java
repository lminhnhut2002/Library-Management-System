/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.Scanner;
import java.util.ArrayList; // Thêm import này
import model.Book;         // Thêm import này
import service.BookService; 



/**
 *
 * @author NHUT
 */
public class MainMenu {

private final Scanner sc = new Scanner(System.in);
    // Tạo duy nhất một instance của BookService để dùng chung cho toàn bộ hệ thống
    private final ArrayList<Book> listBook = new ArrayList<>(); // Tạo list trống
private final BookService bookService = new BookService(listBook); // Truyền list vào đây

    // --- HÀM MAIN: NÚT KHỞI CHẠY CHƯƠNG TRÌNH ---
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.show(); // Gọi hàm hiển thị menu chính
    }

    public void show() {
        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("LIBRARY MANAGEMENT SYSTEM ");
            System.out.println("=======================================");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("3. Borrowing/Returning");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("----------------------------------");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }
            
            switch (choice) {
                case 1:
                    // Khởi tạo BookMenu và truyền dịch vụ bookService vào constructor
                    BookMenu bookMenu = new BookMenu(bookService);
                    
                    // Gọi hàm hiển thị menu quản lý sách của bạn
                    bookMenu.displayMenu(); 
                    break;
                    
                case 2:
                    // Khởi tạo menu quản lý thành viên
                    MemberMenu memberMenu = new MemberMenu();
                    // Hiển thị menu quản lý thành viên
                    memberMenu.displayMenu();

                    break;
                    
                case 3:
                    System.out.println("Tính năng Mượn/Trả sách đang phát triển...");
                    break;
                    
                case 4:
                    System.out.println("Tính năng Báo cáo thống kê đang phát triển...");
                    break;
                    
                case 5:
                    System.out.println("Chào tạm biệt! Hẹn gặp lại.");
                    break;
                    
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 5.");
            }
        } while (choice != 5);
    }
    
}