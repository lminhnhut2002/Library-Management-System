package service;

import java.util.ArrayList;
import model.Book;
import repository.BookRepository;
import utils.Validation;

public class BookService {

    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public ArrayList<Book> getBooks() {
        return repository.getBooks();
    }

    // Tìm sách theo ID
    public Book findByID(String bookID) throws Exception {
        if (Validation.isEmpty(bookID)) {
            throw new Exception("Book ID null.");
        }

        if (!Validation.checkBookID(bookID)) {
            throw new Exception("Invalid Book ID.");
        }

        return repository.findByID(bookID);
    }

    // Thêm sách
    public void addBook(Book book) throws Exception {
        if (book == null) {
            throw new Exception("Book cannot be null.");
        }

        if (findByID(book.getBookID()) != null) {
            throw new Exception("Book ID already exists.");
        }

        if (Validation.isEmpty(book.getTitle())
                || Validation.isEmpty(book.getAuthor())
                || Validation.isEmpty(book.getGenre())) {
            throw new Exception("Title, author and genre cannot be empty.");
        }

        if (!Validation.checkYear(book.getYear())) {
            throw new Exception("Invalid Year.");
        }

        if (!Validation.checkQuantity(book.getQuantity())) {
            throw new Exception("Quantity must be greater than 0.");
        }

        book.setTitle(Validation.toTitleCase(book.getTitle()));
        book.setAuthor(Validation.toTitleCase(book.getAuthor()));
        book.setGenre(Validation.toTitleCase(book.getGenre()));

        repository.add(book);
    }

    // Cập nhật sách
    public void updateBook(String bookID, String title, String author,
            String genre, int year, int quantity) throws Exception {

        Book b = findByID(bookID);

        if (b == null) {
            throw new Exception("Book not found.");
        }

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
            if (!Validation.checkYear(year)) {
                throw new Exception("Invalid year.");
            }
            b.setYear(year);
        }

        if (quantity != -1) {
            if (!Validation.checkQuantity(quantity)) {
                throw new Exception("Quantity cannot be negative.");
            }
            b.setQuantity(quantity);
        }
    }

    // Xóa sách
    public void removeBook(String bookID) throws Exception {

        Book b = findByID(bookID);

        if (b == null) {
            throw new Exception("Book not found.");
        }

        repository.remove(b);
    }

    // Tìm kiếm sách
    public ArrayList<Book> searchBooks(String keyword) {
        if (Validation.isEmpty(keyword)) {
            return new ArrayList<>();
        }

        return repository.search(keyword);
    }
}
