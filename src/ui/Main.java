package ui;


import model.Contact;
import model.ContactMap;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    //EFFECTS: constructs a scanner object
    private static Scanner input = new Scanner(System.in);

    /*
    // EFFECTS: creates and shows the user interface with contact list
    private static void createAndShowUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Contacts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new ContactPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: runs the app
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }*/



    // EFFECTS: runs the program
    public static void main(String[] args) throws IOException {
        ContactMap contactMap = new ContactMap();
        contactMap.run();
    }


    public static int mainMenuUI() {
        return input.nextInt();
    }


    public static Contact getNewContactInfoUI() {
        System.out.println();
        Scanner newInput = new Scanner(System.in);
        System.out.println("Name: ");
        String name = newInput.nextLine();
        System.out.println("Phone: ");
        String phone = newInput.nextLine();
        System.out.println("Address: ");
        String address = newInput.nextLine();
        System.out.println("Email: ");
        String email = newInput.nextLine();
        System.out.println("Favorite?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        int n = newInput.nextInt();
        return ContactMap.favoriteOrRegular(n, name, phone, address, email);
    }


    public static int noContactsUI() {
        System.out.println();
        System.out.println("No contacts!");
        System.out.println("Would you like to add a contact?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        return input.nextInt();
    }


    public static String findContactUI() {
        Scanner newInput = new Scanner(System.in);
        System.out.println();
        System.out.println("First or last name: ");
        return newInput.nextLine();
    }


    public static int askEditContactUI() {
        Scanner newNewInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Edit contact?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        return newNewInput.nextInt();
    }


    public static String editContactUIOne() {
        Scanner newInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter full name of contact you would like to edit:");
        return newInput.nextLine();
    }


    public static int editContactUITwo() {
        Scanner newInput = new Scanner(System.in);
        return newInput.nextInt();
    }

    // EFFECTS: get user input for which contact to delete
    public static String deleteContactUI() {
        System.out.println();
        System.out.println("Which contact do you want to delete?");
        System.out.println("Please enter full name:");
        Scanner newInput = new Scanner(System.in);
        return newInput.nextLine();
    }
}

