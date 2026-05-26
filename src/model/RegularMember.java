/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */

public class RegularMember extends Member {

    // Constructor rỗng
    public RegularMember() {
    }

    // Constructor có tham số
    public RegularMember(String memberID,
                         String name,
                         String phone,
                         String email) {

        super(memberID, name, phone, email);
    }

    // Override giới hạn mượn sách
    @Override
    public int getBorrowLimit() {
        return 3;
    }

    // Override tính tiền phạt
    @Override
    public double calculateFine(int overdueDays) {

        return overdueDays * 5000;
    }

    // Override hiển thị thông tin
    @Override
    public void displayInfo() {

        super.displayInfo();

        System.out.println("Type: Regular Member");
        System.out.println("Borrow Limit: " + getBorrowLimit());
    }
}
    
}
