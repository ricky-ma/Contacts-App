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


    // EFFECTS: user input model and flow for main menu
    public static void mainMenu(ContactMap contactMap) {
        int n2 = input.nextInt();
        while (n2 != 7) {
            switchLoop(contactMap, n2);
            contactMap.printMenu();
            n2 = input.nextInt();
        }
    }


    private static void switchLoop(ContactMap contactMap, int n) {
        switch (n) {
            case 1:
                doCase1(contactMap);
                break;
            case 2:
                doCase2(contactMap);
                break;
            case 3:
                doCase3(contactMap);
                break;
            case 4:
                contactMap.printFavorites();
                break;
            case 5:
                contactMap.printAllContacts();
                break;
            case 6:
                doCase6(contactMap);
                break;
            default:
                System.out.println("Invalid input. Only digits 1-6 accepted.");
        }
    }


    private static Contact getNewContactInfoUI() {
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


    private static int newIntInput() {
        Scanner newInput = new Scanner(System.in);
        return newInput.nextInt();
    }


    private static String newStringInput() {
        Scanner newInput = new Scanner(System.in);
        return newInput.nextLine();
    }


    private static void doCase1(ContactMap contactMap) {
        Contact c = getNewContactInfoUI();
        contactMap.addNewContact(c);
    }


    private static void doCase2(ContactMap contactMap) {
        System.out.println();
        System.out.println("First or last name: ");
        String search = newStringInput();
        contactMap.findContact(search);
    }


    private static void doCase3(ContactMap contactMap) {
        System.out.println();
        System.out.println("Enter full name of contact you would like to edit:");
        String name = newStringInput();
        Contact c2 = contactMap.editContact(name);
        int n = newIntInput();
        contactMap.editContactDetails(c2,n);
    }


    private static void doCase6(ContactMap contactMap) {
        System.out.println();
        System.out.println("Which contact do you want to delete?");
        System.out.println("Please enter full name:");
        String name2 = newStringInput();
        contactMap.deleteContact(name2);
    }
}

