/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author NHUT
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import model.Book;
import model.Member;
public class Validation {
    
    // Single Scanner instance shared across all validation methods
    private static final Scanner scanner = new Scanner(System.in);
    
    // validate Emty Input
    public static String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim(); // Removes leading/trailing spaces
            if (!input.isEmpty()) {
                return input; // Returns valid input if it's not empty
            }
            System.out.println("Fail: Input cannot be empty. Please try again.");
        }
    }
    // validate Phone
    public static String getValidPhone(String prompt) {
        // Regex: Matches exactly 9 to 11 numeric digits
        String phoneRegex = "^\\d{9,11}$";
        
        while (true) {
            System.out.print(prompt);
            String phone = scanner.nextLine().trim();
            if (phone.matches(phoneRegex)) {
                return phone;
            }
            // Strict error message output based on specifications
            System.out.println("Fail: Phone must have 9-11 digits.");
        }
    }
    //validate email
    public static String getValidEmail(String prompt) {
        // Regex: Standard email format validation (e.g., xxxx@gmail.com)
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim();
            if (email.matches(emailRegex)) {
                return email;
            }
            // Strict error message output based on specifications
            System.out.println("Fail: Invalid email.");
        }
    }
    // validate Date(dd//mm//yyyy)
    public static String getValidDate(String prompt) {
        // Set up target date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // Prevents Java from auto-correcting invalid dates (e.g., 30/02)

        while (true) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine().trim();
            try {
                dateFormat.parse(dateStr); // Try parsing string to Date object
                return dateStr; // Returns valid date string if successful
            } catch (ParseException e) {
                System.out.println("Fail: Invalid date format. Please use DD/MM/YYYY.");
            }
        }
    }
    // validate Book ID
    public static String getNewBookID(String prompt, ArrayList<Book> bookList) {
        while (true) {
            String id = getNonEmptyInput(prompt); // Reuses empty input validation
            boolean isExist = false;

            // Loop to check if the Book ID is already taken
            for (Book b : bookList) {
                if (b.getBookID().equalsIgnoreCase(id)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                return id; // ID is valid because it's unique
            }
            System.out.println("Fail: Book ID already exists. Please enter a different ID.");
        }
    }
    //validate Member ID
    public static String getNewMemberID(String prompt, ArrayList<Member> memberList) {
        while (true) {
            String id = getNonEmptyInput(prompt); // Reuses empty input validation
            boolean isExist = false;

            // Loop to check if the Member ID is already taken
            for (Member m : memberList) {
                if (m.getMemberID().equalsIgnoreCase(id)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                return id; // ID is valid because it's unique
            }
            System.out.println("Fail: Member ID already exists. Please enter a different ID.");
        }
    }
    // validate int/choice
    public static int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Fail: Choice must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                // Catches character inputs when numbers are required (Handles "Invalid Input")
                System.out.println("Fail: Invalid Input. Please enter a valid number.");
            }
        }
    }
    
}
