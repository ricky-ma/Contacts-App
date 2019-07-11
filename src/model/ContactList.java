package model;

import java.util.ArrayList;
import java.util.Scanner;




public class ContactList {

    private ArrayList<Contact> contacts;

    //EFFECTS: constructs a scanner object
    private Scanner input = new Scanner(System.in);

    // EFFECTS: constructs a new ArrayList for contacts
    public ContactList() {
        contacts = new ArrayList<>();
        run();
    }

    // EFFECTS: returns the number of contacts in the list of contacts
    public int size() {
        return contacts.size();
    }

    // EFFECTS: returns true iff c is in the list of contacts
    public boolean contains(Contact c) {
        return contacts.contains(c);
    }

    // MODIFIES: this
    // EFFECTS: creates a new contact using user inputs, adds contact to list of contacts
    private void addContact(String name, String phone, String address, String email) {
        Contact contact = new Contact(name, phone, address, email);
        contacts.add(contact);
    }

    // REQUIRES: user input n2 == 2
    // EFFECTS: shows a dialog to inform user that a contact is added
    private void createContactAddedMessage() {
        System.out.println("Success! Contact added.");
        //JOptionPane.showMessageDialog(this, "Contact added.", "Success!", JOptionPane.INFORMATION_MESSAGE, null);
    }

    // EFFECTS: prints menuOne to console when called
    private void menuOne() {
        System.out.println("What would you like to do?");
        System.out.println("1. Add or view contacts.");
        System.out.println("2. Exit.");
    }

    // REQUIRES: user input n1 must not equal 2
    // EFFECTS: prints menuTwo to console when called
    private void menuTwo() {
        System.out.println("What would you like to do?");
        System.out.println("1. Add a contact.");
        System.out.println("2. View all contacts.");
        System.out.println("3. Exit.");
    }

    // MODIFIES: contacts
    // REQUIRES: user input n2 must equal 1
    // EFFECTS: prints add-contact interface, where user enters in new data for new contact;
    //          adds contact to contact list
    private void newContact() {
        Scanner newInput = new Scanner(System.in);
        System.out.println("Name: ");
        String name = newInput.nextLine();
        System.out.println("Phone: ");
        String phone = newInput.nextLine();
        System.out.println("Address: ");
        String address = newInput.nextLine();
        System.out.println("Email: ");
        String email = newInput.nextLine();
        addContact(name, phone, address, email);
    }

    // REQUIRES: user input n2 must equal 2
    // EFFECTS: prints contact list into console, showing all details of each contact
    private void printContacts() {
        for (Contact c : contacts) {
            System.out.println("Name: "    + c.getName());
            System.out.println("Phone: "   + c.getPhone());
            System.out.println("Address: " + c.getAddress());
            System.out.println("Email: "   + c.getEmail());
            System.out.println();
        }
    }

    // REQUIRES: contacts.size() == 0
    // EFFECTS: prints no contacts menu, receives user input for whether new contact should be added
    private void noContacts() {
        System.out.println("No contacts!");
        System.out.println("Would you like to add a contact?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int n3 = input.nextInt();
        if (n3 == 1) {
            newContact();
        }
    }

    // MODIFIES: contacts
    // EFFECTS: main flow for adding and viewing contacts
    public void run() {
        menuOne();
        int n1 = input.nextInt();
        while (n1 != 2) {
            menuTwo();
            int n2 = input.nextInt();
            if (n2 == 1) {
                newContact();
                createContactAddedMessage();
            }
            else if (n2 == 2){
                if (contacts.size() == 0) {
                    noContacts();
                }
                else {
                    printContacts();
                }
            }
            else {
                return;
            }
        }
    }
}
