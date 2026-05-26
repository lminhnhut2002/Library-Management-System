/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author NHUT
 */
        import ui.MainMenu;
public class Main {
    public static void main(String[] args) {
        System.out.println("Library Management System");
        // test file run
        System.out.println("=============================================");
        System.out.println("         LIBRARY MANAGEMENT SYSTEM           ");
        System.out.println("=============================================");
        
        // Initializing the application's core user interface console loop
        MainMenu menu = new MainMenu();
        
        // Triggers the continuous menu selection display for user interaction
        menu.display();
    }
    }

