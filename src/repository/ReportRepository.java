package repository;

import java.time.LocalDate;
import java.util.ArrayList;
import model.Book;
import model.BorrowingTransaction;
import model.Member;

public class ReportRepository {

    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<BorrowingTransaction> transactions;

    public ReportRepository(ArrayList<Book> books,
            ArrayList<Member> members,
            ArrayList<BorrowingTransaction> transactions) {

        this.books = books;
        this.members = members;
        this.transactions = transactions;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<BorrowingTransaction> getTransactions() {
        return transactions;
    }

    // Chưa trả và quá hạn
    public ArrayList<BorrowingTransaction> getOverdueBooks() {

        ArrayList<BorrowingTransaction> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned() && today.isAfter(t.getDueDate())) {
                result.add(t);
            }
        }

        return result;
    }

    // Chưa trả
    public ArrayList<BorrowingTransaction> getBorrowedBooks() {

        ArrayList<BorrowingTransaction> result = new ArrayList<>();

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                result.add(t);
            }
        }

        return result;
    }

    // Đếm số lần mượn của thành viên
    public int countBorrowingsOfMember(String memberID) {

        int count = 0;

        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)) {
                count++;
            }
        }

        return count;
    }
}