package repository;

import java.util.ArrayList;
import model.Book;

public class BookRepository {

    private ArrayList<Book> books;

    public BookRepository(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    // Tìm sách theo ID
    public Book findByID(String bookID) {
        for (Book b : books) {
            if (b.getBookID().equalsIgnoreCase(bookID)) {
                return b;
            }
        }
        return null;
    }

    // Thêm sách
    public void add(Book book) {
        books.add(book);
    }

    // Xóa sách
    public void remove(Book book) {
        books.remove(book);
    }

    // Tìm kiếm
    public ArrayList<Book> search(String keyword) {
        ArrayList<Book> result = new ArrayList<>();

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