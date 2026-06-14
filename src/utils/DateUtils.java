package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    // Định nghĩa một chuẩn duy nhất dd/MM/yyyy cho toàn hệ thống
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parseDate(String input) throws Exception {
        if (Validation.isEmpty(input)) {
            throw new Exception("Date cannot be empty.");
        }
        try {
            return LocalDate.parse(input.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new Exception("Invalid date format. Please use DD/MM/YYYY.");
        }
    }

    public static String formatDate(LocalDate date) {
        return date == null ? "Not returned" : date.format(FORMATTER);
    }
}