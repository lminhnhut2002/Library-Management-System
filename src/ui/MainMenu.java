/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.Scanner;

import model.Member;
import model.RegularMember;
import model.PremiumMember;

public class MainMenu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Nhap ID:");
        String id = sc.nextLine();

        System.out.println("Nhap ten:");
        String name = sc.nextLine();

        System.out.println("Nhap phone:");
        String phone = sc.nextLine();

        System.out.println("Nhap email:");
        String email = sc.nextLine();

        System.out.println("1. Regular Member");
        System.out.println("2. Premium Member");

        int choice = sc.nextInt();

        Member member;

        if (choice == 1) {
            member = new RegularMember(id, name, phone, email);
        } else {
            member = new PremiumMember(id, name, phone, email);
        }

        System.out.println("\n===== MEMBER INFO =====");
        member.displayInfo();
    }
}
/**
 *
 * @author NHUT
 */

