package service;

import java.util.ArrayList;
import model.Book;

public class BookService {
//ArrayList là danh sách động có thể thêm xóa dữ liệu 1 cách chủ động.
    // tạo 1 mảng tên là books để lưu Book trong package model

    private ArrayList<Book> books;
// 13-19 là tạo constructor

    public BookService(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
// 20-28 duyệt từng sách,lấy ID,so sánh với ID cần tìm,đúng thì trả về sách,không có thì return null

    public Book findByID(String bookID) {
        for (Book b : books) {
            if (b.getBookID().equalsIgnoreCase(bookID)) {
                return b;
            }
        }
        return null;
    }
//31-40 Lấy ID sách mới so sánh với ID đã có nếu != null thì sách đã tồn tại -> in lỗi /// nếu thiếu 1/3 title,author,ger
    // != null là đã có dữ liệu chẳng gạn vậy

    public void addBook(Book book) throws Exception {
        if (findByID(book.getBookID()) != null) {
            throw new Exception("Book ID already exists.");
        }
//empty chỉ dùng cho String
        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty() || book.getGenre().isEmpty()) {
            throw new Exception("Title, author, and genre cannot be empty.");
        }
        // nếu nhập năm âm thì in lỗi
        if (book.getYear() <= 0) {
            throw new Exception("Invalid year.");
        }

        books.add(book);
    }

    // cập nhật sách thoi
    public void updateBook(String bookID, String title, String author, String genre, int year, int quantity) throws Exception {
        Book b = findByID(bookID);
        if (b == null) {
            throw new Exception("Book not found.");
        }

        b.setTitle(title);
        b.setAuthor(author);
        b.setGenre(genre);
        b.setYear(year);
        b.setQuantity(quantity);
    }

    // xóa sách
    public void removeBook(String bookID) throws Exception {
        Book b = findByID(bookID);
        if (b == null) {
            throw new Exception("Book not found.");
        }

        books.remove(b);
    }
// Search 

    public ArrayList<Book> searchBooks(String keyword) {
        ArrayList<Book> result = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword)
                    || b.getAuthor().toLowerCase().contains(keyword)
                    || b.getGenre().toLowerCase().contains(keyword)) {
                result.add(b);
            }
        }

        return result;
    }

}
