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
    // CONSTRUCTOR
    public Member(String memberID) {
        this.memberID = memberID;
    }
    // GETTER for Member ID 
    public String getMemberID() {
        return memberID;
    }

    // SETTER for Member ID
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }
}

