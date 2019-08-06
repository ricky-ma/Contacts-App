package model;

import model.exceptions.ContactAlreadyExistsException;
import model.interfaces.ContactMapObserver;
import model.interfaces.ContactMapOperators;
import model.interfaces.LoadAndSaveable;
import model.interfaces.Observable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContactMap implements LoadAndSaveable, ContactMapOperators, Observable {

    private Map<String, Contact> contactMap; // String = name of Contact
    private Map<String, Contact> favoritesMap = new HashMap<>();
    private final List<ContactMapObserver> observers = new ArrayList<>();

    public void setContactMap(Map<String, Contact> contactMap) {
        this.contactMap = contactMap;
    }

    public void setFavoritesMap(Map<String, Contact> favoritesMap) {
        this.favoritesMap = favoritesMap;
    }

    public Map<String, Contact> getContactMap() {
        return contactMap;
    }

    public Map<String, Contact> getFavoritesMap() {
        return favoritesMap;
    }


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
        contactMap.put(c.getName(),c);
    }


    // EFFECTS: gets a contact given its name
    public Contact get(String name) {
        return contactMap.get(name);
    }


    public void addObserver(ContactMapObserver o) {
        observers.add(o);
    }


    // MODIFIES: contacts
    // EFFECTS: gets parameters of each contact from contactfile.txt and creates a new RegularContact object
    //          adds newly created contact to contactMap
    public void load(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            try {
                addNewContact(line);
            } catch (ContactAlreadyExistsException e) {
                // skips that contact
            }
        }
    }


    public List<String[]> loadCSV(String fileName) throws IOException {
        List<String[]> content = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
//                try {
//                    addNewCSVContact(line);
//                } catch (ContactAlreadyExistsException e) {
//                    // skips that contact
//                }
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        }
        return content;
    }
//
//
//    public void addNewCSVContact(String contactInfo) throws ContactAlreadyExistsException {
//        ArrayList<String> partsOfLine = splitLineOnRegex(contactInfo, ",");
//        String name = partsOfLine.get(0);
//        String phone = partsOfLine.get(34);
//        String address = partsOfLine.get(36);
//        String email = partsOfLine.get(30);
//        addFavoriteOrRegular(name, phone, address, email, false);
//    }


    // MODIFIES: contactfile.txt
    // EFFECTS: loops through each Contact in contactMap and writes name, phone, address, and email into contactfile.txt
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

    // ADD A CONTACT------------------------------------------------------------------------------------------------
    // REQUIRES: user input n must equal 1
    // EFFECTS: check if contact already exists, throw ContactAlreadyExistsException if true
    //          add contact to contacts if false
    public void addNewContact(String contactInfo) throws ContactAlreadyExistsException {
        ArrayList<String> partsOfLine = splitLineOnRegex(contactInfo, "---");
        String name = partsOfLine.get(0);
        String phone = partsOfLine.get(1);
        String address = partsOfLine.get(2);
        String email = partsOfLine.get(3);
        boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
        addFavoriteOrRegular(name, phone, address, email, favorite);
    }

    // EDIT CONTACT-------------------------------------------------------------------------------------------------
    // EFFECTS: edit contact details
    public void editContact(String contactInfo) {
        ArrayList<String> partsOfLine = splitLineOnRegex(contactInfo, "---");
        String name = partsOfLine.get(0);
        String phone = partsOfLine.get(1);
        String address = partsOfLine.get(2);
        String email = partsOfLine.get(3);
        boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
        editFavoriteOrRegular(name, phone, address, email, favorite);
    }

    // DELETE CONTACT-----------------------------------------------------------------------------------------------
    // EFFECTS: deletes a contact from contacts
    public boolean deleteContact(String name) {
        if (contactMap.containsKey(name) || favoritesMap.containsKey(name)) {
            if (contactMap.get(name).getFavorite()) {
                contactMap.remove(name);
                favoritesMap.remove(name);
                notifyObservers();
            } else {
                contactMap.remove(name);
                notifyObservers();
            }
            return true;
        }
        return false;
    }

    // PRIVATE METHODS--------------------------------------------------------------------------------------------------

    // EFFECTS: splits a line into separate Strings by "---", stores each String into an ArrayList
    private static ArrayList<String> splitLineOnRegex(String line, String regex) {
        String[] splits = line.split(regex);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // EFFECTS: check if contact already exists, throw ContactAlreadyExistsException if true
    private void doesContactExist(Contact contact) throws ContactAlreadyExistsException {
        if (contains(contact)) {
            throw new ContactAlreadyExistsException();
        }
    }

    @SuppressWarnings({"SameReturnValue", "UnusedReturnValue"})
    private boolean addFavoriteOrRegular(String n, String p, String a, String e, Boolean favorite)
                                        throws ContactAlreadyExistsException {
        if (favorite) {
            Contact contact = new FavoriteContact(n, p, a, e, true);
            doesContactExist(contact);
            contactMap.put(n,contact);
            favoritesMap.put(n,contact);
            notifyObservers();
        } else {
            Contact contact = new RegularContact(n, p, a, e, false);
            doesContactExist(contact);
            contactMap.put(n,contact);
            notifyObservers();
        }
        return true;
    }

    @SuppressWarnings({"SameReturnValue", "UnusedReturnValue"})
    private boolean editFavoriteOrRegular(String n, String p, String a, String e, Boolean favorite) {
        if (favorite) {
            Contact contact = new FavoriteContact(n, p, a, e, true);
            contactMap.put(n,contact);
            favoritesMap.put(n,contact);
            notifyObservers();
        } else {
            Contact contact = new RegularContact(n, p, a, e, false);
            contactMap.put(n,contact);
            favoritesMap.remove(n);
            notifyObservers();
        }
        return true;
    }

    private void notifyObservers() {
        for (ContactMapObserver o : observers) {
            o.updateModel();
        }
    }
}