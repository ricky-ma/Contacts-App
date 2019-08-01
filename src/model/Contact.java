package model;

import java.util.Objects;
import java.util.Scanner;


public abstract class Contact {
    // variable initializations
    public String name;
    public String phone;
    public String address;
    public String email;
    public boolean favorite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return favorite == contact.favorite &&
                name.equals(contact.name) &&
                phone.equals(contact.phone) &&
                address.equals(contact.address) &&
                email.equals(contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, address, email, favorite);
    }

    protected Contact(String name, String phone, String address, String email, boolean favorite) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.favorite = favorite;
    }

    // MODIFIES: this
    // EFFECTS: sets contact name, phone, address, and email

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public abstract void setFavorite(boolean favorite, Contact c);

    // EFFECTS: retrieves contact name, phone, address, and email

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public boolean getFavorite() {
        return favorite;
    }


    public boolean checkInput() {
        try {
            this.checkInputName(name);
            this.checkInputPhone(phone);
            this.checkInputEmail(email);
            return true;
        } catch (InvalidInputException e) {
            System.out.println("Invalid input!");
            return false;
        }
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


    // EFFECTS: continue if input name is valid, otherwise throw InvalidInputException
    private void checkInputName(String name) throws InvalidInputException {
        char [] chars = name.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                throw new InvalidInputException();
            }
        }
    }


    // EFFECTS: continue if input phone is valid, otherwise throw InvalidInputException
    private void checkInputPhone(String phone) throws InvalidInputException {
        char [] chars = phone.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c) && c != '-' && c != '(' && c != ')') {
                throw new InvalidInputException();
            }
        }
    }


    // EFFECTS: continue if input email is valid, otherwise throw InvalidInputException
    private void checkInputEmail(String email) throws InvalidInputException {
        if (!email.contains("@") | !email.contains(".")) {
            throw new InvalidInputException();
        }
    }


    // MODIFIES: name of a contact
    // EFFECT: let user change name of a contact
    public void editContactName(Contact c) {
        Scanner newNewInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Name: ");
        String name = newNewInput.nextLine();
        try {
            c.checkInputName(name);
            c.setName(name);
        } catch (InvalidInputException e) {
            System.out.println("Name can only contain letters.");
        }
    }


    // MODIFIES: phone of a contact
    // EFFECT: let user change phone of a contact
    public void editContactPhone(Contact c) {
        Scanner newNewInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Phone: ");
        String phone = newNewInput.nextLine();
        try {
            c.checkInputPhone(phone);
            c.setPhone(phone);
        } catch (InvalidInputException e) {
            System.out.println("Invalid phone number.");
        }
    }


    // MODIFIES: address of a contact
    // EFFECT: let user change address of a contact
    public void editContactAddress(Contact c) {
        Scanner newNewInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Address: ");
        String address = newNewInput.nextLine();
        c.setAddress(address);
    }


    // MODIFIES: email of a contact
    // EFFECT: let user change email of a contact
    public void editContactEmail(Contact c) {
        Scanner newNewInput = new Scanner(System.in);
        System.out.println();
        System.out.println("Email: ");
        String email = newNewInput.nextLine();
        try {
            c.checkInputEmail(email);
            c.setEmail(email);
        } catch (InvalidInputException e) {
            System.out.println("Invalid email address.");
        }
    }


    // MODIFIES: favorite state of a contact
    // EFFECT: flips favorite state of a contact
    public void editContactFavorite(Contact c) {
        if (c.getFavorite()) {
            this.favorite = false;
            System.out.println("Contact unfavorited.");
        } else {
            this.favorite = true;
            System.out.println("Contact favorited!");
        }
    }
}

