package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import model.Book;
import model.BorrowingTransaction;
import model.Member;
import repository.BookRepository;
import repository.BorrowRepository;
import repository.MemberRepository;

public class BorrowService {

    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private BorrowRepository borrowRepository;

    public BorrowService(BookRepository bookRepository,
            MemberRepository memberRepository,
            BorrowRepository borrowRepository) {

        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowRepository = borrowRepository;
    }

    public int countCurrentBorrowedBooks(String memberID) {
        return borrowRepository.countCurrentBorrowedBooks(memberID);
    }

    public Book findBookByID(String bookID) {
        return bookRepository.findByID(bookID);
    }

    public Member findMemberByID(String memberID) {
        return memberRepository.findByID(memberID);
    }

    // Case 1
    public void borrowBook(String memberID, String bookID,
            LocalDate borrowDate) throws Exception {

        Member member = findMemberByID(memberID);

        if (member == null) {
            throw new Exception("Member not found.");
        }

        Book book = findBookByID(bookID);

        if (book == null) {
            throw new Exception("Book not found.");
        }

        if (!book.isAvailable()) {
            throw new Exception("Out of stock.");
        }

        if (borrowRepository.countCurrentBorrowedBooks(memberID)
                >= member.getBorrowLimit()) {
            throw new Exception("Borrow limit exceeded.");
        }

        if (borrowDate == null) {
            throw new Exception("Borrow date cannot be null.");
        }

        if (borrowDate.isAfter(LocalDate.now())) {
            throw new Exception("Borrow date must be current or in the past.");
        }

        BorrowingTransaction active
                = borrowRepository.findActiveBorrow(memberID, bookID);

        if (active != null) {
            throw new Exception("This member has already borrowed this book.");
        }

        String transactionID
                = "T" + (borrowRepository.getAllTransactions().size() + 1);

        BorrowingTransaction transaction
                = new BorrowingTransaction(
                        transactionID,
                        bookID,
                        memberID,
                        borrowDate,
                        member.getNumberDaysBorrow());

        borrowRepository.add(transaction);

        book.borrowBook();
    }

    // Case 2
    public double returnBook(String memberID, String bookID,
            LocalDate returnDate) throws Exception {

        Member member = findMemberByID(memberID);

        if (member == null) {
            throw new Exception("Member not found.");
        }

        Book book = findBookByID(bookID);

        if (book == null) {
            throw new Exception("Book not found.");
        }

        BorrowingTransaction target
                = borrowRepository.findActiveBorrow(memberID, bookID);

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

    // Case 3
    public ArrayList<BorrowingTransaction> getCurrentBorrowedBooks() {
        return borrowRepository.getCurrentBorrowedBooks();
    }

    // Case 4
    public ArrayList<BorrowingTransaction> getBorrowingHistory(String memberID) throws Exception {

        Member member = findMemberByID(memberID);

        if (member == null) {
            throw new Exception("Member not found.");
        }

        ArrayList<BorrowingTransaction> result
                = borrowRepository.getBorrowHistory(memberID);

        if (result.isEmpty()) {
            throw new Exception("No borrowing history found.");
        }

        return result;
    }

    // Case 5
    public LocalDate extendDueDate(String memberID, String bookID, int extraDay) throws Exception {

        Member member = findMemberByID(memberID);

        if (member == null) {
            throw new Exception("Member not found.");
        }

        Book book = findBookByID(bookID);

        if (book == null) {
            throw new Exception("Book not found.");
        }

        if (extraDay <= 0) {
            throw new Exception("Extra day must be greater than 0.");
        }

        BorrowingTransaction target
                = borrowRepository.findActiveBorrow(memberID, bookID);

        if (target == null) {
            throw new Exception("No active borrow transaction found.");
        }

        LocalDate newDueDate = target.getDueDate().plusDays(extraDay);
        target.setDueDate(newDueDate);

        return newDueDate;
    }

    public double getFeeExtendDueDate(String memberID, int extraDays) throws Exception {

        Member member = findMemberByID(memberID);

        if (member == null) {
            throw new Exception("Member not found.");
        }

        return member.getFeeExtendDueDate(extraDays);
    }

    // Case 6
    public void cancelBorrowing(String memberID, String bookID) throws Exception {

        Member member = findMemberByID(memberID);

        if (member == null) {
            throw new Exception("Member not found.");
        }

        Book book = findBookByID(bookID);

        if (book == null) {
            throw new Exception("Book not found.");
        }

        BorrowingTransaction target
                = borrowRepository.findActiveBorrow(memberID, bookID);

        if (target == null) {
            throw new Exception("No active borrow transaction found.");
        }

        long day = ChronoUnit.DAYS.between(target.getBorrowDate(), LocalDate.now());

        if (day > 1) {
            throw new Exception("Cannot cancel!");
        }

        book.returnBook();
        borrowRepository.remove(target);
    }

    // Case 7
    public ArrayList<BorrowingTransaction> getBooksNearDueDate() throws Exception {

        ArrayList<BorrowingTransaction> result
                = borrowRepository.getBooksNearDueDate();

        if (result.isEmpty()) {
            throw new Exception("No books near due date.");
        }

        return result;
    }

    // Case 8
    public ArrayList<BorrowingTransaction> returnAllBorrowedBooks(String memberID,
            LocalDate returnDate) throws Exception {

        Member member = findMemberByID(memberID);

        if (member == null) {
            throw new Exception("Member not found.");
        }

        ArrayList<BorrowingTransaction> listBooks
                = borrowRepository.getCurrentBorrowedBooksByMember(memberID);

        if (listBooks.isEmpty()) {
            throw new Exception("No borrow transaction found.");
        }

        for (BorrowingTransaction t : listBooks) {
            returnBook(memberID, t.getBookID(), returnDate);
        }

        return listBooks;
    }

    // Case 9
    public ArrayList<BorrowingTransaction> getAllTransactions() throws Exception {

        ArrayList<BorrowingTransaction> result
                = borrowRepository.getAllTransactions();

        if (result.isEmpty()) {
            throw new Exception("No borrowing transactions found.");
        }

        return result;
    }

}
