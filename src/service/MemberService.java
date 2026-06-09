/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.Member;

public class MemberService {

    private final ArrayList<Member> members;

    public MemberService() {
        members = new ArrayList<>();
    }

    // Add Member
    public void addMember(Member member) {
        members.add(member);
        System.out.println("Member added successfully!");
    }

    // View All Members
    public void viewAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        System.out.printf("%-8s %-20s %-15s %-25s %-10s %-5s\n",
                "ID", "Name", "Phone", "Email", "Type", "Limit");

        for (Member member : members) {
            member.displayInfo();
        }
    }

    // Find Member By ID
    public Member findById(String id) {
        for (Member member : members) {
            if (member.getMemberID().equalsIgnoreCase(id)) {
                return member;
            }
        }
        return null;
    }

    // Find Member By Name
    public Member findByName(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }

    // Remove Member
    public boolean removeMember(String id) {
        Member member = findById(id);

        if (member != null) {
            members.remove(member);
            return true;
        }

        return false;
    }

    // Update Member
    public boolean updateMember(String id,
                                String newName,
                                String newPhone,
                                String newEmail) {

        Member member = findById(id);

        if (member != null) {
            member.setName(newName);
            member.setPhone(newPhone);
            member.setEmail(newEmail);
            return true;
        }

        return false;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }
}
