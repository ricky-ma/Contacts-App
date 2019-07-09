package ui;


import model.Contact;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

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



    //main UI loop
    public static void main(String[] args) {
        ArrayList<Contact> contacts = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        System.out.println("What would you like to do?");
        System.out.println("1. Add/view contacts.");
        System.out.println("2. Exit.");

        int n1 = input.nextInt();

        while (n1 != 2) {

            System.out.println("What would you like to do?");
            System.out.println("1. Add a contact.");
            System.out.println("2. View all contacts.");
            System.out.println("3. Exit.");

            int n2 = input.nextInt();

            if (n2 == 1) {
                Scanner newInput = new Scanner(System.in);
                System.out.println("Name: ");
                String name = newInput.nextLine();
                System.out.println("Phone: ");
                String phone = newInput.nextLine();
                System.out.println("Address: ");
                String address = newInput.nextLine();
                System.out.println("Email: ");
                String email = newInput.nextLine();

                contacts.add(new Contact(name, phone, address, email));
            }

            else if (n2 == 2){
                for (Contact c : contacts) {
                    System.out.println("Name: "    + c.getName());
                    System.out.println("Phone: "   + c.getPhone());
                    System.out.println("Address: " + c.getAddress());
                    System.out.println("Email: "   + c.getEmail());
                    System.out.println();
                }
            }

            else {
                return;
            }
        }
    }
}

