/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author NHUT
 */
public abstract class Member {

    private String memberID;
    private String name;
    private String phone;
    private String email;

<<<<<<< HEAD
    // 2. Constructor
    public Member() {
    }

    // 3. Constructor 
=======
>>>>>>> 35bec7689b44a61993e6d19bad9de9ecb21670d7
    public Member(String memberID, String name, String phone, String email) {
        this.memberID = memberID;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

<<<<<<< HEAD
    // 5. Getter & Setter for Name
=======
>>>>>>> 35bec7689b44a61993e6d19bad9de9ecb21670d7
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

<<<<<<< HEAD
    // 6. Getter & Setter for Phone
=======
>>>>>>> 35bec7689b44a61993e6d19bad9de9ecb21670d7
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

<<<<<<< HEAD
    // 7. Getter & Setter for Email
=======
>>>>>>> 35bec7689b44a61993e6d19bad9de9ecb21670d7
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract int getBorrowLimit();

    public abstract double calculateFine(int overdueDays);

    public abstract String getType();

    public void displayInfo() {
        System.out.printf("%-8s %-20s %-15s %-25s %-10s %-5d\n",
                memberID, name, phone, email, getType(), getBorrowLimit());
    }

}
