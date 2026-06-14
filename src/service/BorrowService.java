package service;

import java.time.LocalDate;
import java.util.ArrayList;
import model.Book;
import model.Member;
import model.BorrowingTransaction;
import utils.Validation;

public class BorrowService {

    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<BorrowingTransaction> transactions;

    public BorrowService(ArrayList<Book> books, ArrayList<Member> members, ArrayList<BorrowingTransaction> transactions) {
        this.books = books;
        this.members = members;
        this.transactions = transactions;
    }

    public Book findByBookID(String bookID) {
        if (Validation.isEmpty(bookID)) {
            return null;
        }
        for (Book b : books) {
            if (b.getBookID().equalsIgnoreCase(bookID)) {
                return b;
            }
        }
        return null;
    }

    public Member findByMemberID(String memberID) {
        if (Validation.isEmpty(memberID)) {
            return null;
        }
        for (Member m : members) {
            if (m.getMemberID().equalsIgnoreCase(memberID)) {
                return m;
            }
        }
        return null;
    }

    public int countCurrentBorrowedBooks(String memberID) {
        int count = 0;
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)
                    && !t.isReturned()) {
                count++;
            }
        }
        return count;
    }

    public void borrowBook(String memberID, String bookID, LocalDate borrowDate) throws Exception {

        if (Validation.isEmpty(memberID)) {
            throw new Exception("Member ID null.");
        }
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Member ID Invalid.");
        }
        Member member = findByMemberID(memberID);
        if (member == null) {
            throw new Exception("Member not found.");

        }
        if (Validation.isEmpty(bookID)) {
            throw new Exception("Book ID null.");
        }
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Book ID Invalid.");
        }

        Book book = findByBookID(bookID);
        if (book == null) {
            throw new Exception("Book not found.");
        }
        if (!book.isAvailable()) {
            throw new Exception("Out of stock.");
        }

        if (countCurrentBorrowedBooks(memberID) >= member.getBorrowLimit()) {
            throw new Exception("Borrow limit exceeded.");
        }
        if (borrowDate == null) {
            throw new Exception("Borrow date cannot be null.");
        }
        if (borrowDate.isAfter(LocalDate.now())) {
            throw new Exception("Borrow date must be current or in the past.");
        }

        String transactionID = "T" + (transactions.size() + 1);
        BorrowingTransaction t = new BorrowingTransaction(transactionID, bookID, memberID, borrowDate);
        transactions.add(t);
        book.borrowBook();

    }

    public double returnBook(String memberID, String bookID, LocalDate returnDate) throws Exception {
        if (Validation.isEmpty(memberID)) {
            throw new Exception("Member ID cannot be empty.");
        }
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Invalid member ID");
        }
        Member member = findByMemberID(memberID);
        if (member == null) {
            throw new Exception("Member not found.");
        }

        if (Validation.isEmpty(bookID)) {
            throw new Exception("Book ID cannot be empty.");
        }
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Invalid member ID");
        }
        Book book = findByBookID(bookID);
        if (book == null) {
            throw new Exception("Book not found.");
        }
        BorrowingTransaction target = null;
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)
                    && t.getBookID().equalsIgnoreCase(bookID)
                    && !t.isReturned()) {
                target = t;
                break;
            }
        }
        if (target == null) {
            throw new Exception("No active borrow transaction found.");
        }
        if (returnDate == null) {
            throw new Exception("Return date cannot be null.");
        }
        if (returnDate.isBefore(target.getBorrowDate())) {
            throw new Exception("Return date cannot be before borrow date.");
        }

        int overdueDays = target.getOverdueDays(returnDate);
        double fine = member.calculateFine(overdueDays);

        target.markReturned(returnDate, fine);
        book.returnBook();

        return fine;
    }
    

    public ArrayList<BorrowingTransaction> getCurrentBorrowedBooks() {
        ArrayList<BorrowingTransaction> result = new ArrayList<>();
        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                result.add(t);
            }

        }
        return result;
    }

   public ArrayList<BorrowingTransaction> getBorrowingHistory(String memberID) throws Exception {

        ArrayList<BorrowingTransaction> result = new ArrayList<>();
        if (Validation.isEmpty(memberID)) {
            throw new Exception("Member ID cannot be empty.");

        }
        if (!Validation.isValidID(memberID)) {
                throw new Exception("Invalid Member ID.");
            }
        Member member = findByMemberID(memberID);
        if(member == null){
            throw new Exception("Member ID not found.");
        }
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)) {
                result.add(t);
            }
        }
        return result;
    }
}
