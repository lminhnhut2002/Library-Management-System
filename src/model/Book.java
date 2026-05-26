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
   
    // CONSTRUCTOR 
    public Book(String bookID) {
        this.bookID = bookID;
    }

    // GETTER for Book ID 
 public String getBookID() {
    return bookID;
}
    }

    // SETTER for Book ID
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
}
