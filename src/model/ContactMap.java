package model;

import ui.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContactMap implements LoadAndSaveable, ContactMapOperators {

    private Map<String, Contact> contactMap; // String = name of Contact
    private Map<String, Contact> searchResultMap = new HashMap<>();
    private Map<String, Contact> favoritesMap = new HashMap<>();


    // EFFECTS: constructs a new contactMap for contacts
    public ContactMap() {
        contactMap = new HashMap<>();
    }


    // EFFECTS: returns the size of contactMap
    public int size() {
        return contactMap.size();
    }


    // EFFECTS: returns if contactMap is empty
    public boolean isEmpty() {
        return contactMap.isEmpty();
    }


    // EFFECTS: returns whether or not contactMap contains a specific contact
    public boolean contains(Contact c) {
        return contactMap.containsValue(c);
    }


    // MODIFIES: this
    // EFFECTS: adds a contact to contactMap
    public void add(Contact c) {
        contactMap.put(c.name,c);
    }

    // EFFECTS: gets a contact given its name
    public Contact get(String name) {
        return contactMap.get(name);
    }


    // MODIFIES: contactMap
    // EFFECTS: main flow for adding and viewing contacts
    public void run() throws IOException {
        load();
        printMenu();
        Main.mainMenu(this);
        save();
    }


    // MODIFIES: contacts
    // EFFECTS: gets parameters of each contact from contactfile.txt and creates a new RegularContact object
    //          adds newly created contact to contactMap
    public void load() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("contactfile.txt"));
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String name = partsOfLine.get(0);
            String phone = partsOfLine.get(1);
            String address = partsOfLine.get(2);
            String email = partsOfLine.get(3);
            boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
            if (favorite) {
                Contact contact = new FavoriteContact(name, phone, address, email, true);
                contactMap.put(name,contact);
                favoritesMap.put(name,contact);
            } else {
                Contact contact = new RegularContact(name, phone, address, email, false);
                contactMap.put(name,contact);
            }
        }
    }


    // MODIFIES: contactfile.txt
    // EFFECTS: loops through each Contact in contactMap and writes name, phone, address, and email
    //          into contactfile.txt
    //          each Contact object is a new line
    public void save() throws IOException {
        PrintWriter writer = new PrintWriter("contactfile.txt");
        for (Map.Entry<String, Contact> c : contactMap.entrySet()) {
            writer.print(c.getValue().getName() + "---");
            writer.print(c.getValue().getPhone() + "---");
            writer.print(c.getValue().getAddress() + "---");
            writer.print(c.getValue().getEmail() + "---");
            writer.print(c.getValue().getFavorite());
            writer.println();
        }
        writer.close();
    }


    // EFFECTS: helper method for user to create a favorite or regular contact
    public static Contact favoriteOrRegular(int n, String name, String phone, String address, String email) {
        if (n == 1) {
            FavoriteContact contact = new FavoriteContact(name, phone, address, email, true);
            if (contact.checkInput()) {
                return contact;
            }
            return null;
        } else {
            RegularContact contact = new RegularContact(name, phone, address, email, false);
            if (contact.checkInput()) {
                return contact;
            }
            return null;
        }
    }


    // EFFECTS: check if contact already exists, throw ContactAlreadyExistsException if true
    public void doesContactExist(Contact contact) throws ContactAlreadyExistsException {
        if (contains(contact)) {
            throw new ContactAlreadyExistsException();
        }
    }



    // PRIVATE METHODS--------------------------------------------------------------------------------------------------


    // REQUIRES: user input n1 must not equal 2
    // EFFECTS: prints menu to console when called
    public void printMenu() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("1. Add a contact.");
        System.out.println("2. Find a contact.");
        System.out.println("3. Edit a contact.");
        System.out.println("4. View favorite contacts.");
        System.out.println("5. View all contacts.");
        System.out.println("6. Delete a contact.");
        System.out.println("7. Exit.");
    }


    // EFFECTS: splits a line into separate Strings by "---", stores each String into an ArrayList
    private static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split("---");
        return new ArrayList<>(Arrays.asList(splits));
    }

    private static void printContacts(Map<String, Contact> contactMap) {
        for (Map.Entry<String, Contact> c : contactMap.entrySet()) {
            System.out.println();
            System.out.println("Name: " + c.getValue().getName());
            System.out.println("Phone: " + c.getValue().getPhone());
            System.out.println("Address: " + c.getValue().getAddress());
            System.out.println("Email: " + c.getValue().getEmail());
            System.out.println("Favorite: " + c.getValue().getFavorite());
        }
    }



    // 1. ADD A CONTACT------------------------------------------------------------------------------------------------



    // EFFECTS: check if contact already exists, throw ContactAlreadyExistsException if true
    //          add contact to contacts if false
    public void addNewContact(Contact c) {
        try {
            doesContactExist(c);
            try {
                contactMap.put(c.name,c);
                if (c.favorite) {
                    favoritesMap.put(c.name,c);
                }
                createContactAddedMessage();
            } catch (NullPointerException e) {
                System.out.println("Contact not added.");
            }
        } catch (ContactAlreadyExistsException e) {
            System.out.println("Contact already exists!");
        }
    }


    // EFFECTS: shows a dialog to inform user that a contact is added
    private void createContactAddedMessage() {
        System.out.println("Success! Contact added.");
//      JOptionPane.showMessageDialog(this, "RegularContact added.", "Success!", JOptionPane.INFORMATION_MESSAGE, null);
    }



    // 2. FIND A CONTACT-----------------------------------------------------------------------------------------------

    // REQUIRES: user input n2 must equal 2
    // EFFECTS: searches contacts for a specific RegularContact object and prints contact details
    public void findContact(String search) {
        for (Map.Entry<String, Contact> c : contactMap.entrySet()) {
            if (c.getKey().contains(search)) {
                searchResultMap.put(c.getKey(),c.getValue());
            }
        }
        if (searchResultMap.size() == 0) {
            System.out.println();
            System.out.println("No contact found.");
        } else {
            printContacts(searchResultMap);
        }
        searchResultMap.clear();
    }


    // 3. VIEW FAVORITES ----------------------------------------------------------------------------------------------

    // REQUIRES: user input n2 must equal 3
    // EFFECTS: prints contact list into console, showing all details of each contact
    public void printFavorites() {
        printContacts(favoritesMap);
    }



    // 4. VIEW ALL CONTACTS--------------------------------------------------------------------------------------------


    // REQUIRES: user input n2 must equal 4
    // EFFECTS: prints contact list into console if contacts.size != 0, showing all details of each contact
    public boolean printAllContacts() {
        if (contactMap.size() == 0) {
            System.out.println("No contacts!");
        } else {
            printContacts(contactMap);
        }
        return true;
    }



    // 5. DELETE CONTACT-----------------------------------------------------------------------------------------------


    // REQUIRES: user input n2 must equal 4
    // EFFECTS: deletes a contact from contacts
    public void deleteContact(String name) {
        try {
            contactMap.remove(name);
        } catch (NullPointerException e) {
            System.out.println("Contact not found. No contact was deleted.");
        }
        System.out.println("Contact deleted.");
    }



    // EDIT CONTACT---------------------------------------------------------------------------------------------------

    // EFFECTS: edit contact details
    public Contact editContact(String name) {
        try {
            printContactToEdit(name);
            return searchResultMap.get(name);
        } catch (NullPointerException e) {
            System.out.println("No contact found.");
        }
        return null;
    }


    public void editContactDetails(Contact c, int n) {
        if (n == 1) {
            c.editContactName(c);
        } else if (n == 2) {
            c.editContactPhone(c);
        } else if (n == 3) {
            c.editContactAddress(c);
        } else if (n == 4) {
            c.editContactEmail(c);
        } else {
            c.editContactFavorite(c);
        }
    }


    // EFFECTS: prints user-selected contact from searchResults
    private void printContactToEdit(String name) {
        System.out.println();
        System.out.println("1. Name: " + searchResultMap.get(name).getName());
        System.out.println("2. Phone: " + searchResultMap.get(name).getPhone());
        System.out.println("3. Address: " + searchResultMap.get(name).getAddress());
        System.out.println("4. Email: " + searchResultMap.get(name).getEmail());
        System.out.println("5. Favorite: " + searchResultMap.get(name).getFavorite());
        System.out.println();
        System.out.println("What would you like to edit?");
    }
}
