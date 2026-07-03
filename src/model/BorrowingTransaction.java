package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import utils.DateUtils;

public class BorrowingTransaction {

    private String transactionID;
    private String bookID;
    private String memberID;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    private double fine;
    private boolean returned;

    public BorrowingTransaction(String transactionID, String bookID, String memberID, LocalDate borrowDate,int NumberDaysBorrow) {
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.memberID = memberID;
        this.borrowDate = borrowDate;

        this.dueDate = borrowDate.plusDays(NumberDaysBorrow);
        this.returnDate = null;
        this.fine = 0;
        this.returned = false;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    

    public BorrowingTransaction(String transactionID, String bookID, String memberID,
            LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate,
            double fine, boolean returned) {

        this.transactionID = transactionID;
        this.bookID = bookID;
        this.memberID = memberID;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.returned = returned;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getBookID() {
        return bookID;
    }

    public String getMemberID() {
        return memberID;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getFine() {
        return fine;
    }

    public boolean isReturned() {
        return returned;
    }

    public int getOverdueDays(LocalDate currentDate) {
        if (currentDate.isAfter(dueDate)) {
            long days = ChronoUnit.DAYS.between(dueDate, currentDate);
            return (int) days;
        }

        return 0;
    }

    // đánh dấu sách đã trả .
    public void markReturned(LocalDate returnDate, double fine) {
        this.returnDate = returnDate;
        this.fine = fine;
        this.returned = true;
    }

   //  dd/MM/yyyy
    public String toFileString() {
        String returnText = (returnDate == null) ? "null" : returnDate.format(DateUtils.FORMATTER);
        
        return transactionID + "|"
                + bookID + "|"
                + memberID + "|"
                + borrowDate.format(DateUtils.FORMATTER) + "|"
                + dueDate.format(DateUtils.FORMATTER) + "|"
                + returnText + "|"
                + fine + "|"
                + returned;
    }

   
    public void displayTransaction() {
        String borrowText = DateUtils.formatDate(borrowDate);
        String dueText = DateUtils.formatDate(dueDate);
        String returnText = DateUtils.formatDate(returnDate);

        System.out.printf(
                "%-8s %-8s %-8s %-12s %-12s %-15s %-10.0f %-8s\n",
                transactionID,
                bookID,
                memberID,
                borrowText,
                dueText,
                returnText,
                fine,
                returned
        );
    }
}
