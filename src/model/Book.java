package model;

public class Book {
    private String bookID;
    private String title;
    private String author;
    private String genre;
    private int year;
    private int quantity;
    private int borrowCount;

    public Book(String bookID, String title, String author, String genre, int year, int quantity) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.quantity = quantity;
        this.borrowCount = 0;
    }

    public Book(String bookID, String title, String author, String genre, int year, int quantity, int borrowCount) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.quantity = quantity;
        this.borrowCount = borrowCount;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public boolean isAvailable() {
        return quantity > 0;
    }

    
    
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
        return bookID + "|" + title + "|" + author + "|" + genre + "|" + year + "|" + quantity + "|" + borrowCount;
    }

    public void displayInfo() {
        System.out.printf("%-6s %-25s %-22s %-12s %-6d %-4d\n",
                bookID, title, author, genre, year, quantity);
    }
}
