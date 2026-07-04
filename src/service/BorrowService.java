package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    //case 1
    public void borrowBook(String memberID, String bookID, LocalDate borrowDate) throws Exception {

        Member member = findByMemberID(memberID);
        if (member == null) {
            throw new Exception("Member not found.");

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
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)
                    && t.getBookID().equalsIgnoreCase(bookID)
                    && !t.isReturned()) {
                throw new Exception("This member has already borrowed this book.");
            }
        }
        String transactionID = "T" + (transactions.size() + 1);
        BorrowingTransaction t = new BorrowingTransaction(transactionID, bookID, memberID, borrowDate, member.getNumberDaysBorrow());
        transactions.add(t);
        book.borrowBook();

    }

    //case 2
    public double returnBook(String memberID, String bookID, LocalDate returnDate) throws Exception {

        Member member = findByMemberID(memberID);
        if (member == null) {
            throw new Exception("Member not found.");
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
        if (returnDate.isAfter(LocalDate.now())) {
            throw new Exception("Return date cannot be in the future.");
        }

        int overdueDays = target.getOverdueDays(returnDate);
        double fine = member.calculateFine(overdueDays);

        target.markReturned(returnDate, fine);
        book.returnBook();

        return fine;
    }

    //case 3
    public ArrayList<BorrowingTransaction> getCurrentBorrowedBooks() throws Exception {
        ArrayList<BorrowingTransaction> result = new ArrayList<>();
        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                result.add(t);
            }

        }
        if (result.isEmpty()) {
            throw new Exception("No borrowed books found.");
        }

        return result;
    }

    // case 4
    public ArrayList<BorrowingTransaction> getBorrowingHistory(String memberID) throws Exception {

        ArrayList<BorrowingTransaction> result = new ArrayList<>();

        Member member = findByMemberID(memberID);
        if (member == null) {
            throw new Exception("Member ID not found.");
        }
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)) {
                result.add(t);
            }
        }
        if (result.isEmpty()) {
            throw new Exception("No borrowing history found.");
        }
        return result;
    }

    // case 5
    public LocalDate extendDueDate(String memberID, String bookID, int extraDay) throws Exception {
        Member member = findByMemberID(memberID);
        Book book = findByBookID(bookID);
        if (member == null) {
            throw new Exception("Member not found.");
        }
        if (book == null) {
            throw new Exception("Book not found.");

        }
        if (extraDay <= 0) {
            throw new Exception("Extra day must be greater than 0.");
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
        LocalDate newDueDate = target.getDueDate().plusDays(extraDay);
        target.setDueDate(newDueDate);

        return newDueDate;
    }

    public double getFeeExtendDueDate(String memberID, int extraDays) throws Exception {
        Member member = findByMemberID(memberID);
        if (member == null) {
            throw new Exception("Member not found.");
        }
        return member.getFeeExtendDueDate(extraDays);
    }

    // case 6
    public void cancelBorrowing(String memberID, String bookID) throws Exception {
        Member member = findByMemberID(memberID);
        Book book = findByBookID(bookID);
        if (member == null) {
            throw new Exception("Member not found.");
        }
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
        long day = ChronoUnit.DAYS.between(target.getBorrowDate(), LocalDate.now());
        if (day > 1) {
            throw new Exception("Cannot cancel!");
        }
        book.returnBook();
        transactions.remove(target);
    }

    // case 7
    public ArrayList<BorrowingTransaction> getBooksNearDueDate() throws Exception {
        ArrayList<BorrowingTransaction> result = new ArrayList<>();
        for (BorrowingTransaction t : transactions) {
            long leftDays = ChronoUnit.DAYS.between(LocalDate.now(), t.getDueDate());
            if (!t.isReturned()&& leftDays >= 0 && leftDays <= 2) {
                result.add(t);
            }
        }
        if (result.isEmpty()) {
            throw new Exception("No books near due date.");
        }
        return result;
    }

    // case 8
    public ArrayList<BorrowingTransaction> returnAllBorrowedBooks(String memberID, LocalDate returnDate) throws Exception {
        Member member = findByMemberID(memberID);
        ArrayList<BorrowingTransaction> listBooks = new ArrayList<>();
        if (member == null) {
            throw new Exception("Member not found.");
        }
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)
                    && !t.isReturned()) {
                listBooks.add(t);
            }
        }
        if (listBooks.isEmpty()) {
            throw new Exception("No borrow transaction found.");
        }
        for (BorrowingTransaction t : listBooks) {
            returnBook(memberID, t.getBookID(), returnDate
            );
        }

        return listBooks;
    }
// case 9
    public ArrayList<BorrowingTransaction> getAllTransactions() throws Exception{
        if(transactions.isEmpty()){
            throw new Exception("No borrowing transactions found.");
        }
        return transactions;
    }
   
}
