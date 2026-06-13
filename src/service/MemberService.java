package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Member;

public class MemberService {

    private final ArrayList<Member> members;

    public MemberService() {
        members = new ArrayList<>();
    }

    // ================= VALIDATION =================

    public boolean isValidMemberId(String id) {
        return id != null && id.matches("M\\d{3}");
    }

    public boolean isValidPhone(String phone) {
        return phone != null && phone.matches("0\\d{9}");
    }

    public boolean isValidEmail(String email) {
        return email != null
                && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean isValidName(String name) {
        return name != null
                && !name.trim().isEmpty()
                && name.trim().length() >= 2;
    }

    // ================= CHECK EXIST =================

    public boolean existsMemberId(String id) {
        return findById(id) != null;
    }

    // ================= ADD =================

    public boolean addMember(Member member) {

        if (!isValidMemberId(member.getMemberID())) {
            System.out.println("Invalid Member ID!");
            return false;
        }

        if (existsMemberId(member.getMemberID())) {
            System.out.println("Member ID already exists!");
            return false;
        }

        members.add(member);
        return true;
    }

    // ================= VIEW =================

    public void viewAllMembers() {

        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-25s %-10s %-10s\n",
                "ID", "NAME", "PHONE", "EMAIL", "TYPE", "LIMIT");

        for (Member member : members) {
            member.displayInfo();
        }
    }

    // ================= FIND BY ID =================

    public Member findById(String id) {

        for (Member member : members) {

            if (member.getMemberID()
                    .equalsIgnoreCase(id)) {

                return member;
            }
        }

        return null;
    }

    // ================= FIND BY NAME =================

    public Member findByName(String name) {

        for (Member member : members) {

            if (member.getName()
                    .equalsIgnoreCase(name)) {

                return member;
            }
        }

        return null;
    }

    // ================= SEARCH KEYWORD =================

    public void searchByKeyword(String keyword) {

        boolean found = false;

        for (Member member : members) {

            if (member.getName()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                member.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching member found.");
        }
    }

    // ================= UPDATE =================

    public boolean updateMember(
        String id,
        String newName,
        String newPhone,
        String newEmail) {

    Member member = findById(id);

    if (member == null) {
        System.out.println("Member not found!");
        return false;
    }

    if (!isValidName(newName)) {
        System.out.println("Invalid Name!");
        return false;
    }

    if (!isValidPhone(newPhone)) {
        System.out.println("Invalid Phone!");
        return false;
    }

    if (!isValidEmail(newEmail)) {
        System.out.println("Invalid Email!");
        return false;
    }

    member.setName(newName);
    member.setPhone(newPhone);
    member.setEmail(newEmail);

    return true;
}
    

    // ================= DELETE =================

    public boolean removeMember(String id) {

        Member member = findById(id);

        if (member == null) {
            return false;
        }

        members.remove(member);
        return true;
    }

    // ================= SORT NAME =================

    public void sortByName() {

        Collections.sort(
                members,
                Comparator.comparing(
                        Member::getName,
                        String.CASE_INSENSITIVE_ORDER));
    }

    // ================= SORT ID =================

    public void sortById() {

        Collections.sort(
                members,
                Comparator.comparing(
                        Member::getMemberID,
                        String.CASE_INSENSITIVE_ORDER));
    }

    // ================= COUNT =================

    public int getTotalMembers() {
        return members.size();
    }

    // ================= EMPTY =================

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }
}