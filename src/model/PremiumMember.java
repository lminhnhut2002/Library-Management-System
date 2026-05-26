/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class PremiumMember extends Member {

    // Constructor rỗng
    public PremiumMember() {
    }

    // Constructor có tham số
    public PremiumMember(String memberID,
                         String name,
                         String phone,
                         String email) {

        super(memberID, name, phone, email);
    }

    // Override giới hạn mượn sách
    @Override
    public int getBorrowLimit() {
        return 10;
    }

    // Override tính tiền phạt
    @Override
    public double calculateFine(int overdueDays) {

        return overdueDays * 2000;
    }

    // Override hiển thị thông tin
    @Override
    public void displayInfo() {

        super.displayInfo();

        System.out.println("Type: Premium Member");
        System.out.println("Borrow Limit: " + getBorrowLimit());
    }
}

/**
 *
 * @author ACER
 */

