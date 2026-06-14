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

    private void addMember() throws Exception {
        System.out.println("----------- ADD MEMBER -----------");
        System.out.print("Member ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        if (!Validation.isValidPhone(phone)) {
            throw new Exception("Phone must have 9-11 digits.");
        }

        if (!Validation.isValidEmail(email)) {
            throw new Exception("Invalid email.");
        }

        System.out.println("Type: 1. Regular  2. Premium");
        System.out.print("Choose: ");
        int type = Integer.parseInt(sc.nextLine());

        Member member;
        if (type == 2) {
            member = new PremiumMember(id, name, phone, email);
        } else {
            member = new RegularMember(id, name, phone, email);
        }

        memberService.addMember(member);
        System.out.println("Member added successfully.");
    }

    private void updateMember() throws Exception {
        System.out.println("----------- UPDATE MEMBER -----------");
        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();

        Member m = memberService.findByID(id);
        if (m == null) {
            throw new Exception("Member not found.");
        }

        System.out.println("Current Information:");
        System.out.println("Name: " + m.getName());
        System.out.println("Phone: " + m.getPhone());
        System.out.println("Email: " + m.getEmail());
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
            memberService.updateMember(id, name, phone, email);
            System.out.println("Member updated successfully.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private void removeMember() throws Exception {
        System.out.println("----------- REMOVE MEMBER -----------");
        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();

        if (borrowService.countCurrentBorrowedBooks(id) > 0) {
            throw new Exception("Cannot remove. Member has outstanding borrowed books.");
        }

        memberService.removeMember(id);
        System.out.println("Member removed successfully.");
    }

    private void searchMember() {
        System.out.print("Enter member name or ID: ");
        String keyword = sc.nextLine();
        displayMembers(memberService.searchMembers(keyword));
    }

    public void displayMembers(ArrayList<Member> members) {
        System.out.println("----------- MEMBER LIST -----------");
        displayHeader();

        for (Member m : members) {
            m.displayInfo();
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
