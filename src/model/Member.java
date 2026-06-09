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

    // 2. Constructor
    public Member() {
    }

    // 3. Constructor 
    public Member(String memberID, String name, String phone, String email) {
        this.memberID = memberID;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // 4. Getter & Setter cho Member ID
    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    // 5. Getter & Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 6. Getter & Setter for Phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // 7. Getter & Setter for Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayInfo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


    