package repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import model.BorrowingTransaction;

public class BorrowRepository {

    private ArrayList<BorrowingTransaction> transactions;

    public BorrowRepository(ArrayList<BorrowingTransaction> transactions) {
        this.transactions = transactions;
    }

    public void setTransactions(ArrayList<BorrowingTransaction> transactions) {
        this.transactions = transactions;
    }

    // Thêm giao dịch
    public void add(BorrowingTransaction transaction) {
        transactions.add(transaction);
    }

    // Xóa giao dịch
    public void remove(BorrowingTransaction transaction) {
        transactions.remove(transaction);
    }

    // Tìm giao dịch đang mượn
    public BorrowingTransaction findActiveBorrow(String memberID, String bookID) {
        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)
                    && t.getBookID().equalsIgnoreCase(bookID)
                    && !t.isReturned()) {
                return t;
            }
        }
        return null;
    }

    // Đếm số sách đang mượn
    public int countCurrentBorrowedBooks(String memberID) {
        int count = 0;

        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)
                    && !t.isReturned()) {
                count++;
            }
        }

        return count;
    }

    // Lấy tất cả giao dịch của thành viên
    public ArrayList<BorrowingTransaction> getBorrowHistory(String memberID) {
        ArrayList<BorrowingTransaction> result = new ArrayList<>();

        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)) {
                result.add(t);
            }
        }

        return result;
    }

    // Lấy tất cả giao dịch đang mượn
    public ArrayList<BorrowingTransaction> getCurrentBorrowedBooks() {
        ArrayList<BorrowingTransaction> result = new ArrayList<>();

        for (BorrowingTransaction t : transactions) {
            if (!t.isReturned()) {
                result.add(t);
            }
        }

        return result;
    }
    // Lấy các sách sắp đến hạn

    public ArrayList<BorrowingTransaction> getBooksNearDueDate() {
        ArrayList<BorrowingTransaction> result = new ArrayList<>();

        for (BorrowingTransaction t : transactions) {
            long leftDays = ChronoUnit.DAYS.between(LocalDate.now(), t.getDueDate());

            if (!t.isReturned() && leftDays >= 0 && leftDays <= 2) {
                result.add(t);
            }
        }

        return result;
    }
    // Lấy các sách đang mượn của một thành viên

    public ArrayList<BorrowingTransaction> getCurrentBorrowedBooksByMember(String memberID) {

        ArrayList<BorrowingTransaction> result = new ArrayList<>();

        for (BorrowingTransaction t : transactions) {
            if (t.getMemberID().equalsIgnoreCase(memberID)
                    && !t.isReturned()) {
                result.add(t);
            }
        }

        return result;
    }
    // Lấy tất cả giao dịch

    public ArrayList<BorrowingTransaction> getAllTransactions() {
        return transactions;
    }
}
