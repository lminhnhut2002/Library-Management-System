/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author NHUT
 */
public class Book {

    private String bookID;
     private String title;
    private String author;
   
    private String genre;
    private int year;
    private int quantity;
    private int borrowCount;

    public Book(String bookID, String title, String author, String genre, int year, int quantity, int borrowCount) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.quantity = quantity;
        this.borrowCount = borrowCount;
    }

    public Book(String bookID, String title, String author, String genre, int year, int quantity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public Book() {
    }

    //  so luong sach co phai > 0
    public boolean isAvailable() {
        return quantity > 0;
    }

    // ham dieu kien cho muon sach
    public boolean borrowBook() {
        if (quantity > 0) {
            quantity--;
            borrowCount++;
            return true;
        }
        return false;
    }

    public void returnBook() {
        quantity++;
    }

    public String toFileString() {
        return bookID + "|" + title + "|" + author + "|" + genre + "|" + year + "|"  + quantity + "|" + borrowCount;
    }
    
    
   
public void displayInfo(){
    System.out.printf("%-8s %-25s %-20s %-15s %-6d %-8d %-8d\n",
            bookID,title,author,genre,year,quantity,borrowCount);
}

    /**
     *
     * @author ACER
     */
    public static abstract class PremiumMember extends Member {

        public PremiumMember(String memberID, String name, String phone, String email) {
            super(memberID, name, phone, email);
        }

        @Override
        public int getBorrowLimit() {
            return 5;
        }
    }
}
