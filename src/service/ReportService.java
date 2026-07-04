package service;

import java.util.ArrayList;
import java.util.Comparator;
import model.Book;
import model.BorrowingTransaction;
import model.Member;
import repository.ReportRepository;

public class ReportService {

    private ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    // Chưa trả và quá hạn
    public ArrayList<BorrowingTransaction> getOverdueBooks() {
        return repository.getOverdueBooks();
    }

    // Chưa trả
    public ArrayList<BorrowingTransaction> getBorrowedBooks() {
        return repository.getBorrowedBooks();
    }

    // Sách được mượn nhiều nhất
    public ArrayList<Book> getMostPopularBooks() {

        ArrayList<Book> result = new ArrayList<>(repository.getBooks());

        result.sort(
                Comparator.comparingInt(Book::getBorrowCount).reversed()
        );

        return result;
    }

    // Đếm số lần mượn của một thành viên
    public int countBorrowingsOfMember(String memberID) {
        return repository.countBorrowingsOfMember(memberID);
    }

    // Thành viên mượn nhiều nhất
    public ArrayList<Member> getTopBorrowingMembers() {

        ArrayList<Member> result = new ArrayList<>(repository.getMembers());

        result.sort((m1, m2) -> Integer.compare(
                countBorrowingsOfMember(m2.getMemberID()),
                countBorrowingsOfMember(m1.getMemberID())
        ));

        return result;
    }
}