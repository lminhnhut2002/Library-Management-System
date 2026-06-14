package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import model.Book;
import model.Member;
import model.BorrowingTransaction;
import utils.Validation;

public class ReportService {

    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<BorrowingTransaction> transactions;

    public ReportService(ArrayList<Book> books, ArrayList<Member> members, ArrayList<BorrowingTransaction> transactions) {
        this.books = books;
        this.members = members;
        this.transactions = transactions;
    }
// chưa trả và quá hạn

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
// chưa trả

    public ArrayList<BorrowingTransaction> getBorrowedBooks() {
        ArrayList<BorrowingTransaction> result = new ArrayList<>();

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                result.add(t);
            }
        }

        return result;
    }

    public ArrayList<Book> getMostPopularBooks() {
        ArrayList<Book> result = new ArrayList<>(books);
        result.sort(Comparator.comparingInt(Book::getBorrowCount).reversed());
        return result;
    }

    public int countBorrowingsOfMember(String memberID) {
        int count = 0;
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)) {
                count++;
            }
        }

        return count;
    }

    public ArrayList<Member> getTopBorrowingMembers() {
        ArrayList<Member> result = new ArrayList<>(members);

        result.sort((m1, m2) -> Integer.compare(
                countBorrowingsOfMember(m2.getMemberID()),
                countBorrowingsOfMember(m1.getMemberID())
        ));

        return result;
    }
}
