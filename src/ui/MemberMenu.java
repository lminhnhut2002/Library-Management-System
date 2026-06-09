/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import java.util.Scanner;
import model.Member;
import model.RegularMember;
import model.PremiumMember;
import service.MemberService;

/**
 *
 * @author ACER
 */
public class MemberMenu {
    





    Scanner sc = new Scanner(System.in);
    MemberService service = new MemberService();

    public void addMember() {

        System.out.print("Enter ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.println("1. Regular");
        System.out.println("2. Premium");

        int type = Integer.parseInt(sc.nextLine());

        Member member;

        if(type == 1){
            member = new RegularMember(id, name, phone, email);
        } else {
            member = new PremiumMember(id, name, phone, email);
        }

       service.addMember(member);
    }

    public void displayMenu() {

    int choice;

    do {
        System.out.println("\n================================");
        System.out.println("      MEMBER MANAGEMENT");
        System.out.println("================================");
        System.out.println("1. Add Member");
        System.out.println("2. View Members");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(sc.nextLine());

        switch(choice) {

            case 1:
                addMember();
                break;

            case 2:
                service.viewAllMembers();
                break;

            case 0:
                System.out.println("Back to Main Menu...");
                break;

            default:
                System.out.println("Invalid choice!");
        }

    } while(choice != 0);
}
}

    
    

