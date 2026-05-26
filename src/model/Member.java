/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author NHUT
 */
public class Member {




    private String memberID;
    private String name;
    private String phone;
    private String email;

    // Constructor rỗng
    public Member() {
    }

    // Constructor có tham số
    public Member(String memberID, String name, String phone, String email) {

        this.memberID = memberID;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getter & Setter
    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Hiển thị thông tin
    public void displayInfo() {

        System.out.println("ID: " + memberID);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
    }

    // Giới hạn mượn sách
    public int getBorrowLimit() {
        return 3;
    }

    // Tính tiền phạt
    public double calculateFine(int overdueDays) {
        return overdueDays * 5000;
    }
}