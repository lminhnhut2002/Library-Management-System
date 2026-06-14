package utils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Book;
import model.Member;
import model.RegularMember;
import model.PremiumMember;
import model.BorrowingTransaction;

public class FileUtils {
    private static final String BOOK_FILE = "data/books.txt";
    private static final String MEMBER_FILE = "data/members.txt";
    private static final String TRANSACTION_FILE = "data/transactions.txt";

    public static void saveBooks(ArrayList<Book> books) throws IOException {
        new File("data").mkdirs();
        PrintWriter pw = new PrintWriter(new FileWriter(BOOK_FILE));
        for (Book b : books) {
            pw.println(b.toFileString());
        }
        pw.close();
    }

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File(BOOK_FILE);
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length == 7) {
                    books.add(new Book(
                            p[0], p[1], p[2], p[3],
                            Integer.parseInt(p[4]),
                            Integer.parseInt(p[5]),
                            Integer.parseInt(p[6])
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot load books file.");
        }

        return books;
    }

    public static void saveMembers(ArrayList<Member> members) throws IOException {
        new File("data").mkdirs();
        PrintWriter pw = new PrintWriter(new FileWriter(MEMBER_FILE));
        for (Member m : members) {
            pw.println(m.toFileString());
        }
        pw.close();
    }

    public static ArrayList<Member> loadMembers() {
        ArrayList<Member> members = new ArrayList<>();
        File file = new File(MEMBER_FILE);
        if (!file.exists()) {
            return members;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length == 5) {
                    if (p[0].equalsIgnoreCase("Premium")) {
                        members.add(new PremiumMember(p[1], p[2], p[3], p[4]));
                    } else {
                        members.add(new RegularMember(p[1], p[2], p[3], p[4]));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot load members file.");
        }

        return members;
    }

    public static void saveTransactions(ArrayList<BorrowingTransaction> transactions) throws IOException {
        new File("data").mkdirs();
        PrintWriter pw = new PrintWriter(new FileWriter(TRANSACTION_FILE));
        for (BorrowingTransaction t : transactions) {
            pw.println(t.toFileString());
        }
        pw.close();
    }

    public static ArrayList<BorrowingTransaction> loadTransactions() {
        ArrayList<BorrowingTransaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTION_FILE);
        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length == 8) {
                    // Sửa đổi quan trọng: Đọc ngày tháng theo đúng chuẩn cấu hình chung dd/MM/yyyy
                    LocalDate borrowDate = LocalDate.parse(p[3], DateUtils.FORMATTER);
                    LocalDate dueDate = LocalDate.parse(p[4], DateUtils.FORMATTER);
                    LocalDate returnDate = p[5].equals("null") ? null : LocalDate.parse(p[5], DateUtils.FORMATTER);

                    transactions.add(new BorrowingTransaction(
                            p[0], p[1], p[2], borrowDate, dueDate, returnDate,
                            Double.parseDouble(p[6]),
                            Boolean.parseBoolean(p[7])
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot load transactions file.");
        }

        return transactions;
    }
}