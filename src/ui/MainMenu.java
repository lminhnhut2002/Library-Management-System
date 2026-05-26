/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author NHUT
 */
import utils.Validation;
public class MainMenu {
   // test file run
        public void display() {
        while (true) {
            System.out.println("\n=============================================");
            System.out.println("         LIBRARY MANAGEMENT SYSTEM           ");
            System.out.println("=============================================");
            System.out.println("1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Borrowing / Returning");
            System.out.println("4. Reports");
            System.out.println("5. File I/O");
            System.out.println("0. Exit System");
            System.out.println("=============================================");
            
            // Sử dụng hàm getValidInt từ utils.Validation để bắt lỗi nhập chữ an toàn
            int choice = Validation.getValidInt("Enter your choice: ", 0, 5);
            
            if (choice == 0) {
                System.out.println("Goodbye! System closed.");
                break; // Thoát chương trình
            }
            
            // Xử lý các lựa chọn menu
            switch (choice) {
                case 1:
                    System.out.println("Redirecting to Book Management...");
                    // Sau này bạn sẽ gọi BookMenu ở đây
                    break;
                case 2:
                    System.out.println("Redirecting to Member Management...");
                    // Sau này bạn sẽ gọi MemberMenu ở đây
                    break;
                case 3:
                    System.out.println("Redirecting to Borrowing/Returning...");
                    break;
                case 4:
                    System.out.println("Redirecting to Reports...");
                    break;
                case 5:
                    System.out.println("Redirecting to File I/O...");
                    break;
            }
        }
    }
    }

    
    

