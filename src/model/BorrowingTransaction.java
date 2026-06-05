/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author NHUT
 */
public class BorrowingTransaction {

    private String transactionID;
    private String bookID;
    private String memberID;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    private double fine;
    // nếu sách chưa trả sẽ return về false và ngược lại
    private boolean returned;

    public BorrowingTransaction(String transcationID, String bookID, String memberID, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, double fine, boolean returned) {
        this.transactionID = transcationID;
        this.bookID = bookID;
        this.memberID = memberID;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.returned = returned;
    }

    public BorrowingTransaction(String transactionID, String bookID, String memberID, LocalDate borrowDate) {
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.memberID = memberID;
        this.borrowDate = borrowDate;
    }

    public String getTranscationID() {
        return transactionID;
    }

    public void setTranscationID(String transcationID) {
        this.transactionID = transcationID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
//  lố hạn trả

    public int getOverdueDays(LocalDate currentDate) {
        if (currentDate.isAfter(dueDate)) {
            long days = ChronoUnit.DAYS.between(dueDate, currentDate);
            return (int) days;
        }

        return 0;
    }

    // đánh dấu sách đã trả 
    public void markReturned(LocalDate returnDate, double fine) {
        this.returnDate = returnDate;
        this.fine = fine;
        this.returned = true;
    }

    //Hàm này dùng để chuyển một đối tượng BorrowingTransaction thành 1 dòng String để lưu vào file
    // để save data
    public String toFileString() {
        String returnText;

        if (returnDate == null) {
            returnText = "null";
        } else {
            // muốn ghép dữ liệu vào file thì phải dùng 
            returnText = returnDate.toString();
        }

        String line = transactionID + "|"
                + bookID + "|"
                + memberID + "|"
                + borrowDate + "|"
                + dueDate + "|"
                + returnText + "|"
                + fine + "|"
                + returned;

        return line;
    }

    public void displayInfo() {
        String returnText;
        if (returnDate == null) {
            returnText = "Not returned";

        } else {
            returnText = returnDate.toString();

        }
        System.out.printf("%-8s %-8s %-8s %-12s %-12s %-15s %-10.0f %-8s\n",
                transactionID,
                bookID,
                memberID,
                borrowDate,
                dueDate,
                returnText,
                fine,
                returned
        );
    }
}
