package model;

import java.util.List;

public class FavoriteContact extends Contact {

    private List<Contact> contacts;

    // EFFECTS: constructs new contact object
    public FavoriteContact(String name, String phone, String address, String email, boolean favorite){
        super(name, phone, address, email, favorite);
    }

//    public void addFavorite(Contact c) {
//        if (!favorites.contains(c)) {
//            favorites.add(c);
//        }
//    }

    public void addContact(RegularContact c) {
        if (!contacts.contains(c)) {
            contacts.add(c);
            c.addFavorite(this);
        }
    }

}
