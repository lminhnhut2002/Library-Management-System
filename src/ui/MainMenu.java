/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.Scanner;

/**
 *
 * @author NHUT
 */
public class MainMenu {

    Scanner sc = new Scanner(System.in);

    public void show() {
        int choice;
        do {

            System.out.println("\n======================================");
            System.out.println("LIBRARY MANAGEMENT SYSTEM ");
            System.out.println("=======================================");
            System.out.println("1.Manage Books");
            System.out.println("2.Manage Members");
            System.out.println("3.Borrowing/Returning");
            System.out.println("4.Reports");
            System.out.println("5.Exit");
            System.out.println("----------------------------------");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }
            switch (choice) {
                case 1:

                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                default:
                    System.out.print("Invalid option");
            }
        } while (choice != 5);

    }
   

}
