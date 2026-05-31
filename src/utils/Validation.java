/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author NHUT
 */
public class Validation {
//có thể áp dụng cho title ,author,...

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return !isEmpty(email)
                && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isValidPhone(String phone) {
        return !isEmpty(phone)
                && phone.matches("\\d{9,11}");
    }

    public static boolean isPositiveNumber(int n) {
        return n > 0;
    }

    public static boolean checkQuantity(int quantity) {
        return quantity > 0;
    }

    public static boolean isValidID(String id) {
        return !isEmpty(id)
                && id.matches("^SE\\d{6,8}$");
    }

    public static boolean checkBookID(String bookID) {
        return !isEmpty(bookID)
                && bookID.matches("^BK\\d{6,8}$");

    }

    public static boolean checkYear(int year) {
        return year > 0 && year <= java.time.Year.now().getValue();
    }
}
