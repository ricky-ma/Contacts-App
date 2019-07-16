package model;


import java.awt.*;

public class Contact extends Component {


    // variable initializations
    public String name;
    public String phone;
    public String address;
    public String email;


    // EFFECTS: constructs new contact object
    public Contact(String name, String phone, String address, String email){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }


    // MODIFIES: this
    // EFFECTS: sets contact name, phone, address, and email
    @Override
    public void setName(String name) { this.name = name; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setAddress(String address) { this.address = address; }

    public void setEmail(String email) { this.email = email; }


    // EFFECTS: retrieves contact name, phone, address, and email
    @Override
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


}
