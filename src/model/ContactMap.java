package model;

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


    // MODIFIES: contacts
    // EFFECTS: gets parameters of each contact from contactfile.txt and creates a new RegularContact object
    //          adds newly created contact to contactMap
    public void load(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            addNewContact(line);
        }
    }


    // MODIFIES: contactfile.txt
    // EFFECTS: loops through each Contact in contactMap and writes name, phone, address, and email
    //          into contactfile.txt
    //          each Contact object is a new line
    public void save(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(fileName);
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

    // 1. ADD A CONTACT------------------------------------------------------------------------------------------------
    // REQUIRES: user input n must equal 1
    // EFFECTS: check if contact already exists, throw ContactAlreadyExistsException if true
    //          add contact to contacts if false
    public boolean addNewContact(String contactInfo) {
        ArrayList<String> partsOfLine = splitOnDashes(contactInfo);
        String name = partsOfLine.get(0);
        String phone = partsOfLine.get(1);
        String address = partsOfLine.get(2);
        String email = partsOfLine.get(3);
        boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
        return addFavoriteOrRegular(name, phone, address, email, favorite);
    }

    // 2. FIND A CONTACT-----------------------------------------------------------------------------------------------
    // REQUIRES: user input n must equal 2
    // EFFECTS: searches contacts for a specific RegularContact object and prints contact details
    public boolean findContact(String search) {
        for (Map.Entry<String, Contact> c : contactMap.entrySet()) {
            if (c.getKey().contains(search)) {
                searchResultMap.put(c.getKey(),c.getValue());
            }
        }
        if (searchResultMap.size() == 0) {
            System.out.println();
            System.out.println("No contact found.");
            return false;
        } else {
            printContacts(searchResultMap);
        }
        searchResultMap.clear();
        return true;
    }

    // 3. EDIT CONTACT-------------------------------------------------------------------------------------------------
    // REQUIRES: user input n must equal 3
    // EFFECTS: edit contact details
    public Contact editContact(String name) {
        try {
            printContactToEdit(name);
            return contactMap.get(name);
        } catch (NullPointerException e) {
            System.out.println("No contact found.");
        }
        return null;
    }

    // 4. VIEW FAVORITES ----------------------------------------------------------------------------------------------
    // REQUIRES: user input n must equal 4
    // EFFECTS: prints contact list into console, showing all details of each contact
    public boolean printFavorites() {
        printContacts(favoritesMap);
        return true;
    }

    // 5. VIEW ALL CONTACTS--------------------------------------------------------------------------------------------
    // REQUIRES: user input n must equal 5
    // EFFECTS: prints contact list into console if contacts.size != 0, showing all details of each contact
    public boolean printAllContacts() {
        if (contactMap.size() == 0) {
            System.out.println("No contacts!");
            return false;
        } else {
            printContacts(contactMap);
            return true;
        }
    }

    // 6. DELETE CONTACT-----------------------------------------------------------------------------------------------
    // REQUIRES: user input n must equal 6
    // EFFECTS: deletes a contact from contacts
    public boolean deleteContact(String name) {
        if (contactMap.containsKey(name)) {
            contactMap.remove(name);
            System.out.println("Contact deleted.");
            return true;
        } else {
            System.out.println("Contact not found. No contact was deleted.");
            return false;
        }
    }




    // PRIVATE METHODS--------------------------------------------------------------------------------------------------

    // EFFECTS: splits a line into separate Strings by "---", stores each String into an ArrayList
    private static ArrayList<String> splitOnDashes(String line) {
        String[] splits = line.split("---");
        return new ArrayList<>(Arrays.asList(splits));
    }

    // EFFECTS: check if contact already exists, throw ContactAlreadyExistsException if true
    private void doesContactExist(Contact contact) throws ContactAlreadyExistsException {
        if (contains(contact)) {
            throw new ContactAlreadyExistsException();
        }
    }

    private boolean addFavoriteOrRegular(String n, String p, String a, String e, Boolean favorite) {
        try {
            if (favorite) {
                Contact contact = new FavoriteContact(n, p, a, e, true);
                doesContactExist(contact);
                contactMap.put(n,contact);
                favoritesMap.put(n,contact);
            } else {
                Contact contact = new RegularContact(n, p, a, e, false);
                doesContactExist(contact);
                contactMap.put(n,contact);
            }
            return true;
        } catch (ContactAlreadyExistsException exception) {
            System.out.println("Contact already exists!");
            return false;
        }
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

    // EFFECTS: prints user-selected contact from searchResults
    private void printContactToEdit(String name) {
        System.out.println();
        System.out.println("1. Name: " + contactMap.get(name).getName());
        System.out.println("2. Phone: " + contactMap.get(name).getPhone());
        System.out.println("3. Address: " + contactMap.get(name).getAddress());
        System.out.println("4. Email: " + contactMap.get(name).getEmail());
        System.out.println("5. Favorite: " + contactMap.get(name).getFavorite());
        System.out.println();
        System.out.println("What would you like to edit?");
    }
}