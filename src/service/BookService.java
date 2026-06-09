/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.Book;
import utils.Validation;

/**
 *
 * @author NHUT
 */
public class BookService {
    // taọ 1 mảng books để chưa các Book trong package model

    private ArrayList<Book> books;

    public BookService(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public Book findByID(String bookID) {
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

    public void addBook(Book book) throws Exception {
        if (book == null) {
            throw new Exception("Book cannot be null.");
        }
        if (!Validation.checkBookID(book.getBookID())) {
            throw new Exception("Invalid Book ID.");
        }
        if (findByID(book.getBookID()) != null) {
            throw new Exception("Book ID already exists.");
        }
        if (!Validation.isEmpty(book.getTitle())
                || Validation.isEmpty(book.getAuthor())
                || Validation.isEmpty(book.getGenre())) {

            throw new Exception("Title,author and genre cannot be empty.");
        }
        if (!Validation.checkYear(book.getYear())) {
            throw new Exception("Invalid Year.");
        }
        if (!Validation.checkQuantity(book.getQuantity())) {
            throw new Exception("Quantity must be greater than 0.");
        }

        books.add(book);

    }

    // update book
    public void updateBook(String bookID, String title, String author, String genre, int year, int quantity) throws Exception {
        Book b = findByID(bookID);
        if (b == null) {
            throw new Exception("Book not found.");
        }

        if (Validation.isEmpty(title)
                || Validation.isEmpty(author)
                || Validation.isEmpty(genre)) {
            throw new Exception("Title ,author and genre cannot be empty.");
        }
        if (!Validation.checkYear(year)) {
            throw new Exception("Invalid Year.");
        }
        if (Validation.checkQuantity(quantity)) {
            throw new Exception("Quantity must be greater than 0.");
        }

        b.setTitle(title);
        b.setAuthor(author);
        b.setGenre(genre);
        b.setYear(year);
        b.setQuantity(quantity);
    }

//xóa sách
    public void removeBook(String bookID) throws Exception {
        Book b = findByID(bookID);
        if (b == null) {
            throw new Exception("Book not found.");
        }
        books.remove(b);

    }

    //Search book
    public ArrayList<Book> searchBook(String keyword) {
        ArrayList<Book> result = new ArrayList<>();
        if (Validation.isEmpty(keyword)) {
            return result;
        }
        keyword = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword)
                    || b.getAuthor().toLowerCase().contains(keyword)
                    || b.getGenre().toLowerCase().contains(keyword)
                    || b.getBookID().toLowerCase().contains(keyword)) {
                result.add(b);
            }

        }
        return result;
    }

    public List<Book> searchBooks(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Book> getAllBooks() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean updateBook(String bookID, String newTitle, String newGenre, int newYear) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Book getBookById(String bookID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
