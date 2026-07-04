package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Member;
import model.RegularMember;
import model.PremiumMember;
import service.MemberService;
import service.BorrowService;
import utils.Validation;

public class MemberMenu {

    private Scanner sc;
    private MemberService memberService;
    private BorrowService borrowService;

    public MemberMenu(Scanner sc, MemberService memberService, BorrowService borrowService) {
        this.sc = sc;
        this.memberService = memberService;
        this.borrowService = borrowService;
    }

    public void show() {
        int choice;

        do {
            System.out.println("\n----------- MANAGE MEMBERS -----------");
            System.out.println("1. Add Member");
            System.out.println("2. Update Member");
            System.out.println("3. Remove Member");
            System.out.println("4. View All Members");
            System.out.println("5. Search Member");
            System.out.println("6. Back");
            System.out.print("Choose: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            try {
                switch (choice) {
                    case 1:
                        addMember();
                        break;
                    case 2:
                        updateMember();
                        break;
                    case 3:
                        removeMember();
                        break;
                    case 4:
                        displayMembers(memberService.getMembers());
                        break;
                    case 5:
                        searchMember();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Fail: " + e.getMessage());
            }

        } while (choice != 6);
    }

    //case 1
    private void addMember() throws Exception {
        System.out.println("----------- ADD MEMBER -----------");

        String memberID = Validation.inputRequired(sc, "Member ID: ", "Member ID").toUpperCase();
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Invalid Member ID.");
        }
        String name = Validation.inputRequired(sc, "Name: ", "Name");
        String phone = Validation.inputRequired(sc, "Phone: ", "Phone");
        if (!Validation.isValidPhone(phone)) {
            throw new Exception("Phone must have 9-11 digits.");
        }
        String email = Validation.inputRequired(sc, "Email: ", "Email");
        if (!Validation.isValidEmail(email)) {
            throw new Exception("Invalid email.");
        }
        System.out.println("Type:");
        System.out.println("1. Regular");
        System.out.println("2. Premium");
        int type = Integer.parseInt(
                Validation.inputRequired(sc, "Choose: ", "Type"));
        if (type != 1 && type != 2) {
            throw new Exception("Type must be 1 or 2.");
        }
        Member member;
        if (type == 1) {
            member = new RegularMember(memberID, name, phone, email);
        } else {
            member = new PremiumMember(memberID, name, phone, email);
        }
        memberService.addMember(member);
        System.out.println("Member added successfully.");
    }
// case 2

    private void updateMember() throws Exception {
        System.out.println("----------- UPDATE MEMBER -----------");
        String memberID = Validation.inputRequired(sc, "Member ID: ", "Member ID").toUpperCase();
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Invalid Member ID.");
        }
        Member member = memberService.findByID(memberID);
        if (member == null) {
            throw new Exception("Member not found.");
        }
        System.out.println("Current Information:");
        displayHeader();
        member.displayInfo();
        System.out.println("(Leave blank and press ENTER to skip updating that field)");
        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Phone: ");
        String phone = sc.nextLine();
        System.out.print("New Email: ");
        String email = sc.nextLine();
        System.out.print("[1] Update [2] Cancel: ");
        String confirm = sc.nextLine();
        if (confirm.equals("1")) {
            memberService.updateMember(memberID, name, phone, email);
            System.out.println("Member updated successfully.");
        } else {
            System.out.println("Cancelled.");
        }
    }
    // case3

    private void removeMember() throws Exception {
        System.out.println("----------- REMOVE MEMBER -----------");
        String memberID = Validation.inputRequired(sc, "Member ID: ", "Member ID").toUpperCase();
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Invalid Member ID.");
        }
        Member member = memberService.findByID(memberID);
        if (member == null) {
            throw new Exception("Member not found.");
        }
        if (borrowService.countCurrentBorrowedBooks(memberID) > 0) {
            throw new Exception("Cannot remove. Member has outstanding borrowed books.");
        }
        memberService.removeMember(memberID);
        System.out.println("Member removed successfully.");
    }
    //case 4

    private void searchMember() {
        try {
            String keyword = Validation.inputRequired(
                    sc,
                    "Enter member name or ID: ",
                    "Keyword");
            displayMembers(memberService.searchMembers(keyword));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayMembers(ArrayList<Member> members) {
        System.out.println("----------- MEMBER LIST -----------");
        displayHeader();
        if (members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            for (Member m : members) {
                m.displayInfo();
            }
        }

        System.out.print("Press ENTER to return...");
        sc.nextLine();
    }

    private void displayHeader() {
        System.out.printf("%-8s %-20s %-15s %-25s %-10s %-5s\n",
                "ID", "Name", "Phone", "Email", "Type", "Limit");
        System.out.println("--------------------------------------------------------------------------------");
    }
}
