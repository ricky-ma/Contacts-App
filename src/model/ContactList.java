package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContactList implements LoadAndSaveable, ContactListOperators {

    private Map<String, Contact> contactMap = new HashMap<>(); // String = name of Contact
    private List<Contact> contacts;
    private ArrayList<Contact> searchResults = new ArrayList<>();


    //EFFECTS: constructs a scanner object
    private Scanner input = new Scanner(System.in);


    // EFFECTS: constructs a new ArrayList for contacts
    public ContactList() {
        contacts = new ArrayList<>();
        run();
    }


    // EFFECTS: returns the size of contacts
    public int size() {
        return contacts.size();
    }


    // EFFECTS: returns if contacts is empty
    public boolean isEmpty() {
        return contacts.isEmpty();
    }


    // EFFECTS: returns whether or not contacts contains a specific contact
    public boolean contains(Contact c) {
        return contacts.contains(c);
    }


    // MODIFIES: this
    // EFFECTS: adds a contact to contacts
    public void add(Contact c) {
        contacts.add(c);
    }

    // EFFECTS: gets a contact given its index
    public Contact get(int i) {
        return contacts.get(i);
    }


//    // EFFECTS: gets the list inside contacts
//    public List<Contact> getList(ContactList contacts) {
//        for (Contact c : contacts) {
//
//        }
//    }


    // EFFECTS: check if contact already exists, throw ContactAlreadyExistsException if true
    public void doesContactExist(Contact contact) throws ContactAlreadyExistsException {
        for (Contact c : contacts) {
            if (c.getName().equals(contact.getName())) {
                throw new ContactAlreadyExistsException();
            }
        }
    }


    // EFFECTS: splits a line into separate Strings by "---", stores each String into an ArrayList
    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split("---");
        return new ArrayList<>(Arrays.asList(splits));
    }


    // MODIFIES: contacts
    // EFFECTS: gets parameters of each contact from contactfile.txt and creates a new RegularContact object
    //          adds newly created contact to contacts
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
                contacts.add(contact);
            } else {
                Contact contact = new RegularContact(name, phone, address, email, false);
                contacts.add(contact);
            }
        }
    }


    // MODIFIES: contactfile.txt
    // EFFECTS: loops through each RegularContact in contacts and writes name, phone, address, and email
    //          into contactfile.txt
    //          each RegularContact object is a new line
    public void save() {
        try {
            PrintWriter writer = new PrintWriter("contactfile.txt");
            for (Contact c : contacts) {
                writer.print(c.getName()    + "---");
                writer.print(c.getPhone()   + "---");
                writer.print(c.getAddress() + "---");
                writer.print(c.getEmail()   + "---");
                writer.print(c.getFavorite());
                writer.println();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("New file cannot be found.");
        }
    }


    // MODIFIES: contacts
    // EFFECTS: main flow for adding and viewing contacts
    public void run() {
        try {
            load();
        } catch (IOException e) {
            System.out.println("Loadable file not found.");
        }
        menuOne();
        int n1 = input.nextInt();
        while (n1 != 2) {
            menuTwo();
            getInputForMenuTwo();
            save();
        }
    }




    // PRIVATE METHODS

    // REQUIRES: user input n2 == 2
    // EFFECTS: shows a dialog to inform user that a contact is added
    private void createContactAddedMessage() {
        System.out.println("Success! Contact added.");
//      JOptionPane.showMessageDialog(this, "RegularContact added.", "Success!", JOptionPane.INFORMATION_MESSAGE, null);
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
        System.out.println("3. View favorite contacts.");
        System.out.println("4. View all contacts.");
        System.out.println("5. Delete a contact.");
        System.out.println("6. Exit.");
    }


    // EFFECTS: user input model and flow for menuTwo
    private void getInputForMenuTwo() {
        int n2 = input.nextInt();
        switch (n2) {
            case 1: getNewContactInfo();
                break;
            case 2: findContact();
                break;
            case 3: printFavorites();
                break;
            case 4: noContacts(contacts.size() == 0);
                    printContacts(contacts.size() == 0);
                break;
            case 5: deleteContact();
                break;
            case 6: System.exit(0);
                break;
            default: System.out.println("Invalid input. Only digits 1-6 accepted.");;
        }
    }


    // EFFECTS: prints no contacts menu if contacts.size == 0,
    //          receives user input for whether new contact should be added
    private void noContacts(boolean noContacts) {
        if (noContacts) {
            System.out.println();
            System.out.println("No contacts!");
            System.out.println("Would you like to add a contact?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int n3 = input.nextInt();
            if (n3 == 1) {
                getNewContactInfo();
            }
        }
    }


    // REQUIRES: user input n2 must equal 1
    // EFFECTS: prints add-contact interface, where user enters in new data for new contact
    private void getNewContactInfo() {
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
        addContactToContacts(favoriteOrRegular(n, name, phone, address, email));
    }


    // EFFECTS: helper method for user to create a favorite or regular contact
    private Contact favoriteOrRegular(int n, String name, String phone, String address, String email) {
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
    //          add contact to contacts if false
    private void addContactToContacts(Contact c) {
        try {
            doesContactExist(c);
            contacts.add(c);
            createContactAddedMessage();
        } catch (ContactAlreadyExistsException e) {
            System.out.println("Contact already exists!");
        }
    }


    // REQUIRES: user input n2 must equal 2
    // EFFECTS: searches contacts for a specific RegularContact object and prints contact details
    private void findContact() {
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
            printSearchResults();
            askEditContact();
        }
    }


    // EFFECTS: prints contact search results to console
    private void printSearchResults() {
        int counter = 1;
        for (Contact c : searchResults) {
            System.out.println();
            System.out.println(counter++ + ".");
            System.out.println("Name: " + c.getName());
            System.out.println("Phone: " + c.getPhone());
            System.out.println("Address: " + c.getAddress());
            System.out.println("Email: " + c.getEmail());
            System.out.println("Favorite: " + c.getFavorite());
        }
    }


    // EFFECTS: ask user if he/she wants to edit a contact
    private void askEditContact() {
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


    // EFFECTS: edit contact details
    private void editContact() {
        Scanner newInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Which contact?");
        int n1 = newInput.nextInt();
        printSelectedSearchResult(n1);
        Contact c = searchResults.get(n1 - 1);
        int n2 = newInput.nextInt();
        if (n2 == 1) {
            c.editContactName(c);
        } else if (n2 == 2) {
            c.editContactPhone(c);
        } else if (n2 == 3) {
            c.editContactAddress(c);
        } else if (n2 == 4) {
            c.editContactEmail(c);
        } else {
            c.editContactFavorite(c);
        }
    }


    // EFFECTS: prints user-selected contact from searchResults
    private void printSelectedSearchResult(int n1) {
        System.out.println();
        System.out.println("1. Name: " + searchResults.get(n1 - 1).getName());
        System.out.println("2. Phone: " + searchResults.get(n1 - 1).getPhone());
        System.out.println("3. Address: " + searchResults.get(n1 - 1).getAddress());
        System.out.println("4. Email: " + searchResults.get(n1 - 1).getEmail());
        System.out.println("5. Favorite: " + searchResults.get(n1 - 1).getFavorite());
        System.out.println();
        System.out.println("What would you like to edit?");
    }


    // REQUIRES: user input n2 must equal 3
    // EFFECTS: prints contact list into console if contacts.size != 0, showing all details of each contact
    private void printContacts(boolean noContacts) {
        if (!noContacts) {
            for (Contact c : contacts) {
                System.out.println();
                System.out.println("Name: " + c.getName());
                System.out.println("Phone: " + c.getPhone());
                System.out.println("Address: " + c.getAddress());
                System.out.println("Email: " + c.getEmail());
                System.out.println("Favorite: " + c.getFavorite());
            }
        }
    }


    // REQUIRES: user input n2 must equal 3
    // EFFECTS: prints contact list into console, showing all details of each contact
    private void printFavorites() {
        for (Contact c : contacts) {
            if (c.favorite) {
                System.out.println();
                System.out.println("Name: "    + c.getName());
                System.out.println("Phone: "   + c.getPhone());
                System.out.println("Address: " + c.getAddress());
                System.out.println("Email: "   + c.getEmail());
            }
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
            System.out.println("Favorite: " + c.getFavorite());
        }
        Scanner newInput = new Scanner(System.in);
        int n = newInput.nextInt();
        contacts.remove(n - 1);
        System.out.println("Contact deleted.");
    }
}
