package service;

import java.util.ArrayList;
import model.Book;
import utils.Validation;

public class BookService {
//ArrayList là danh sách động có thể thêm xóa dữ liệu 1 cách chủ động.

    // tạo 1 mảng tên là books để lưu Book trong package model
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

    //Chỉ tìm 1 cuốn sách duy nhất theo ID.
//Dùng cho:
//Add Book (check trùng ID)
//Update Book
//Delete Book
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
// Lấy ID sách mới so sánh với ID đã có nếu != null thì sách đã tồn tại -> in lỗi /// nếu thiếu 1/3 title,author,ger
    // != null là đã có dữ liệu chẳng gạn vậy

    public void addBook(Book book) throws Exception {
        if (book == null) {
            throw new Exception("Book cannot be null.");
        }
// check cho bookID
        if (!Validation.checkBookID(book.getBookID())) {
            throw new Exception("Invalid Book ID.");
        }
        if (findByID(book.getBookID()) != null) {
            throw new Exception("Book ID already exists.");
        }

//check điều kiện rỗng cho title ,author và genre
        if (Validation.isEmpty(book.getTitle())
                || Validation.isEmpty(book.getAuthor())
                || Validation.isEmpty(book.getGenre())) {
            throw new Exception("Title, author and genre cannot be empty.");
        }
// nếu nhập năm âm thì in lỗi
        if (!Validation.checkYear((book.getYear()))) {
            throw new Exception("Invalid Year.");
        }
        if (!Validation.checkQuantity((book.getQuantity()))) {
            throw new Exception("Quantity must be greater than 0.");
        }

        book.setTitle(Validation.toTitleCase(book.getTitle()));
        book.setAuthor(Validation.toTitleCase(book.getAuthor()));
        book.setGenre(Validation.toTitleCase(book.getGenre()));
        books.add(book);
    }

    // cập nhật sách 
    // Cập nhật sách: nếu để trống/bằng 0 (hoặc -1 với số) thì giữ nguyên giá trị cũ
    public void updateBook(String bookID, String title, String author, String genre, int year, int quantity) throws Exception {
        if (Validation.isEmpty(bookID)) {
            throw new Exception("Book ID null.");
        }
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Invalid Book ID.");
        }
        Book b = findByID(bookID);
        if (b == null) {
            throw new Exception("Book not found.");
        }

        // Nếu có nhập mới thì mới cập nhật và chuẩn hóa chữ hoa/thường
        if (!Validation.isEmpty(title)) {
            b.setTitle(Validation.toTitleCase(title));
        }
        if (!Validation.isEmpty(author)) {
            b.setAuthor(Validation.toTitleCase(author));
        }
        if (!Validation.isEmpty(genre)) {
            b.setGenre(Validation.toTitleCase(genre));
        }
        if (year != -1) {
            if (!Validation.checkYear(year)) throw new Exception("Invalid year.");
            b.setYear(year);
        }
        if (quantity != -1) {
            if (!Validation.checkQuantity(quantity)) throw new Exception("Quantity cannot be negative.");
            b.setQuantity(quantity);
        }
    }

    // xóa sách
    public void removeBook(String bookID) throws Exception {
        if (Validation.isEmpty(bookID)) {
            throw new Exception("Book ID null.");
        }
        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Invalid Book ID.");
        }
        Book b = findByID(bookID);
        if (b == null) {
            throw new Exception("Book not found.");
        }

        books.remove(b);
    }
// Search 

    public ArrayList<Book> searchBooks(String keyword) {
        //Tạo một danh sách rỗng tên là result để chứa các đối tượng Book.
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

}
