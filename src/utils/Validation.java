package utils;

public class Validation {

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
        return quantity >= 0; // Sửa lại >= 0 vì khi mượn hết sách, quantity có thể bằng 0
    }

    // Đề bài: M001, M002 -> Chữ M đầu theo sau là các chữ số
    public static boolean isValidID(String id) {
        return !isEmpty(id) && id.matches("^[Mm]\\d+$");
    }

    // Đề bài: B001, B002 -> Chữ B đầu theo sau là các chữ số
    public static boolean checkBookID(String bookID) {
        return !isEmpty(bookID) && bookID.matches("^[Bb]\\d+$");
    }

    public static boolean checkYear(int year) {
        return year > 0 && year <= java.time.Year.now().getValue();
    }
    
    public static String toTitleCase(String text) {
        if (isEmpty(text)) {
            return text;
        }

        String[] words = text.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1))
                  .append(" ");
        }

        return result.toString().trim();
    }
}