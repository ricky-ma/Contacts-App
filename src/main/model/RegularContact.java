package model;


import java.util.List;

public class RegularContact extends Contact {

    private List<Contact> favorites;

    // EFFECTS: constructs new contact object
    public RegularContact(String name, String phone, String address, String email, boolean favorite){
        super(name, phone, address, email, favorite);
    }



//    // MODIFIES: favorites
//    // EFFECTS: adds a favorite contact to favorites
//    protected void addFavorite(FavoriteContact fc) {
//        if (!favorites.contains(fc)) {
//            favorites.add(fc);
//            fc.addContact(this);
//        }
//    }
}
