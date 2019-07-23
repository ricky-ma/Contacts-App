package model;

public class FavoriteContact extends Contact {

    // EFFECTS: constructs new contact object
    public FavoriteContact(String name, String phone, String address, String email, boolean favorite){
        super(name, phone, address, email, favorite);
    }


    public void setFavorite(boolean favorite) {
        this.favorite = true;
    }

}
