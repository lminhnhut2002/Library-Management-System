package ui;

import java.util.Scanner;
import model.Member;
import model.PremiumMember;
import model.RegularMember;
import service.MemberService;

public class MemberMenu {

    private final Scanner sc = new Scanner(System.in);
    private final MemberService service = new MemberService();

    // ================= ADD MEMBER =================
    public void addMember() {

        String id;

        while (true) {

            System.out.print("Enter ID (M001): ");
            id = sc.nextLine().trim();

            if (!service.isValidMemberId(id)) {
                System.out.println("Invalid ID format! Example: M001");
                continue;
            }

            if (service.existsMemberId(id)) {
                System.out.println("Member ID already exists!");
                continue;
            }

            break;
        }

        String name;

        do {
            System.out.print("Enter Name: ");
            name = sc.nextLine();

            if (!service.isValidName(name)) {
                System.out.println("Invalid Name!");
            }

        } while (!service.isValidName(name));

        String phone;

        do {
            System.out.print("Enter Phone: ");
            phone = sc.nextLine();

            if (!service.isValidPhone(phone)) {
                System.out.println("Invalid Phone!");
            }

        } while (!service.isValidPhone(phone));

        String email;

        do {
            System.out.print("Enter Email: ");
            email = sc.nextLine();

            if (!service.isValidEmail(email)) {
                System.out.println("Invalid Email!");
            }

        } while (!service.isValidEmail(email));

        int type;

        while (true) {

            try {

                System.out.println("1. Regular Member");
                System.out.println("2. Premium Member");
                System.out.print("Choose Type: ");

                type = Integer.parseInt(sc.nextLine());

                if (type == 1 || type == 2) {
                    break;
                }

            } catch (Exception e) {
            }

            System.out.println("Please choose 1 or 2.");
        }

        Member member;

        if (type == 1) {
            member = new RegularMember(id, name, phone, email);
        } else {
            member = new PremiumMember(id, name, phone, email);
        }

        if (service.addMember(member)) {
            System.out.println("Member added successfully!");
        }
    }

    // ================= FIND ID =================
    public void findById() {

        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();

        Member member = service.findById(id);

        if (member == null) {
            System.out.println("Member not found!");
        } else {
            member.displayInfo();
        }
    }

    // ================= FIND NAME =================
    public void findByName() {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        Member member = service.findByName(name);

        if (member == null) {
            System.out.println("Member not found!");
        } else {
            member.displayInfo();
        }
    }

    // ================= SEARCH KEYWORD =================
    public void searchByKeyword() {

        System.out.print("Enter Keyword: ");
        String keyword = sc.nextLine();

        service.searchByKeyword(keyword);
    }

    // ================= UPDATE =================
    public void updateMember() {

        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();

        Member member = service.findById(id);

        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        System.out.print("New Name: ");
        String name = sc.nextLine();

        System.out.print("New Phone: ");
        String phone = sc.nextLine();

        System.out.print("New Email: ");
        String email = sc.nextLine();

        if (!service.isValidName(name)) {
            System.out.println("Invalid Name!");
            return;
        }

        if (!service.isValidPhone(phone)) {
            System.out.println("Invalid Phone!");
            return;
        }

        if (!service.isValidEmail(email)) {
            System.out.println("Invalid Email!");
            return;
        }

        if (service.updateMember(id, name, phone, email)) {
            System.out.println("Update successful!");
        }
    }

    // ================= DELETE =================
    public void deleteMember() {

        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();

        if (service.removeMember(id)) {
            System.out.println("Delete successful!");
        } else {
            System.out.println("Member not found!");
        }
    }

    // ================= MENU =================
    public void displayMenu() {

        int choice;

        do {

            System.out.println("\n====================================");
            System.out.println("        MEMBER MANAGEMENT");
            System.out.println("====================================");
            System.out.println("1. Add Member");
            System.out.println("2. View All Members");
            System.out.println("3. Find Member By ID");
            System.out.println("4. Find Member By Name");
            System.out.println("5. Search By Keyword");
            System.out.println("6. Update Member");
            System.out.println("7. Remove Member");
            System.out.println("8. Sort By Name");
            System.out.println("9. Sort By ID");
            System.out.println("10. Count Members");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {

                case 1:
                    addMember();
                    break;

                case 2:
                    service.viewAllMembers();
                    break;

                case 3:
                    findById();
                    break;

                case 4:
                    findByName();
                    break;

                case 5:
                    searchByKeyword();
                    break;

                case 6:
                    updateMember();
                    break;

                case 7:
                    deleteMember();
                    break;

                case 8:
                    service.sortByName();
                    System.out.println("Sorted by Name.");
                    service.viewAllMembers();
                    break;

                case 9:
                    service.sortById();
                    System.out.println("Sorted by ID.");
                    service.viewAllMembers();
                    break;

                case 10:
                    System.out.println(
                            "Total Members: "
                            + service.getTotalMembers());
                    break;

                case 0:
                    System.out.println("Back to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }
}