package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ContactList implements LoadAndSaveable {

    
    private List<Contact> contacts;
    private ArrayList<Contact> searchResults = new ArrayList<>();


    //EFFECTS: constructs a scanner object
    private Scanner input = new Scanner(System.in);


    // EFFECTS: constructs a new ArrayList for contacts
    public ContactList() throws IOException {
        contacts = new ArrayList<>();
        run();
    }


    // EFFECTS: splits a line into separate Strings by "---", stores each String into an ArrayList
    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split("---");
        return new ArrayList<>(Arrays.asList(splits));
    }


    // MODIFIES: contacts
    // EFFECTS: gets parameters of each contact from contactfile.txt and creates a new Contact object
    //          adds newly created contact to contacts
    public void load() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("contactfile.txt"));
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String name = partsOfLine.get(0);
            String phone = partsOfLine.get(1);
            String address = partsOfLine.get(2);
            String email = partsOfLine.get(3);
            Contact contact = new Contact(name, phone, address, email);
            contacts.add(contact);
        }
    }


    // MODIFIES: contactfile.txt
    // EFFECTS: loops through each Contact in contacts and writes name, phone, address, and email into contactfile.txt
    //          each Contact object is a new line
    public void save() throws IOException {
        PrintWriter writer = new PrintWriter("contactfile.txt");
        for (Contact c : contacts) {
            writer.print(c.getName()    + "---");
            writer.print(c.getPhone()   + "---");
            writer.print(c.getAddress() + "---");
            writer.print(c.getEmail());
            writer.println();
        }
        writer.close();
    }


    // MODIFIES: contacts
    // EFFECTS: main flow for adding and viewing contacts
    public void run() throws IOException {
        load();
        menuOne();
        int n1 = input.nextInt();
        while (n1 != 2) {
            menuTwo();
            int n2 = input.nextInt();
            if (n2 == 1) {
                newContact();
                createContactAddedMessage();
            } else if (n2 == 2) {
                viewContact();
            } else if (n2 == 3) {
                if (contacts.size() == 0) {
                    noContacts();
                } else {
                    printContacts();
                }
            } else if (n2 == 4) {
                deleteContact();
            } else {
                return;
            }
            save();
        }
    }




    // PRIVATE METHODS

    // REQUIRES: user input n2 == 2
    // EFFECTS: shows a dialog to inform user that a contact is added
    private void createContactAddedMessage() {
        System.out.println("Success! Contact added.");
        //JOptionPane.showMessageDialog(this, "Contact added.", "Success!", JOptionPane.INFORMATION_MESSAGE, null);
    }


    // EFFECTS: prints menuOne to console when called
    private void menuOne() {
        System.out.println("1. Manage Contacts");
        System.out.println("2. Exit");
    }


    // REQUIRES: user input n1 must not equal 2
    // EFFECTS: prints menuTwo to console when called
    private void menuTwo() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1. Add a contact.");
        System.out.println("2. Find a contact.");
        System.out.println("3. View all contacts.");
        System.out.println("4. Delete a contact.");
        System.out.println("5. Exit.");
    }


    // REQUIRES: contacts.size() == 0
    // EFFECTS: prints no contacts menu, receives user input for whether new contact should be added
    private void noContacts() {
        System.out.println();
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
    // REQUIRES: user input n2 must equal 1
    // EFFECTS: prints add-contact interface, where user enters in new data for new contact;
    //          adds contact to contact list
    private void newContact() {
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
        Contact contact = new Contact(name, phone, address, email);
        contacts.add(contact);
    }


    // REQUIRES: user input n2 must equal 2
    // EFFECTS: searches contacts for a specific Contact object and prints contact details
    private void viewContact() {
        Scanner newInput = new Scanner(System.in);
        System.out.println();
        System.out.println("First or last name: ");
        String name = newInput.nextLine();
        for (Contact c : contacts) {
            if (c.getName().contains(name)) {
                searchResults.add(c);
            }
        }
        if (searchResults.size() == 0) {
            System.out.println();
            System.out.println("No contact found.");
        } else {
            int counter = 1;
            for (Contact c : searchResults) {
                System.out.println();
                System.out.println(counter++ + ".");
                System.out.println("Name: " + c.getName());
                System.out.println("Phone: " + c.getPhone());
                System.out.println("Address: " + c.getAddress());
                System.out.println("Email: " + c.getEmail());
            }
            Scanner newNewInput = new Scanner(System.in);
            System.out.println();
            System.out.println("Edit contact?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int n = newNewInput.nextInt();
            if (n == 1) {
                editContact();
            }
        }
    }


    // EFFECTS: edit contact details
    private void editContact() {
        Scanner newInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Which contact?");
        int n1 = newInput.nextInt();
        System.out.println();
        System.out.println("1. Name: " + searchResults.get(n1 - 1).getName());
        System.out.println("2. Phone: " + searchResults.get(n1 - 1).getPhone());
        System.out.println("3. Address: " + searchResults.get(n1 - 1).getAddress());
        System.out.println("4. Email: " + searchResults.get(n1 - 1).getEmail());
        System.out.println();
        System.out.println("What would you like to edit?");
        int n2 = newInput.nextInt();
        if (n2 == 1) {
            Scanner newNewInput = new Scanner(System.in);
            System.out.println();
            System.out.println("Name: ");
            String name = newNewInput.nextLine();
            searchResults.get(n1 - 1).setName(name);
        } else if (n2 == 2) {
            Scanner newNewInput = new Scanner(System.in);
            System.out.println();
            System.out.println("Phone ");
            String phone = newNewInput.nextLine();
            searchResults.get(n1 - 1).setPhone(phone);
        } else if (n2 == 3) {
            Scanner newNewInput = new Scanner(System.in);
            System.out.println();
            System.out.println("Address ");
            String address = newNewInput.nextLine();
            searchResults.get(n1 - 1).setAddress(address);
        } else {
            Scanner newNewInput = new Scanner(System.in);
            System.out.println();
            System.out.println("Email ");
            String email = newNewInput.nextLine();
            searchResults.get(n1 - 1).setEmail(email);
        }
    }


    // REQUIRES: user input n2 must equal 3
    // EFFECTS: prints contact list into console, showing all details of each contact
    private void printContacts() {
        for (Contact c : contacts) {
            System.out.println();
            System.out.println("Name: "    + c.getName());
            System.out.println("Phone: "   + c.getPhone());
            System.out.println("Address: " + c.getAddress());
            System.out.println("Email: "   + c.getEmail());
        }
    }


    // REQUIRES: user input n2 must equal 4
    // EFFECTS: deletes a contact from contacts
    private void deleteContact() {
        System.out.println();
        System.out.println("Which contact do you want to delete?");
        int counter = 1;
        for (Contact c : contacts) {
            System.out.println();
            System.out.println(counter++ + ".");
            System.out.println("Name: " + c.getName());
            System.out.println("Phone: " + c.getPhone());
            System.out.println("Address: " + c.getAddress());
            System.out.println("Email: " + c.getEmail());
        }
        Scanner newInput = new Scanner(System.in);
        int n = newInput.nextInt();
        contacts.remove(n - 1);
    }
}
