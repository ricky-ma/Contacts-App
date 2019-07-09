package model;


import javax.swing.*;
import java.awt.*;

public class Contact extends Component {

    //fields
    private String name;
    private String phone;
    private String address;
    private String email;
    /*private String facebook;
    private String instagram;
    private String twitter;
    private String wechat;*/

    //Constructor
    public Contact(String n, String p, String a, String e/*, String f, String i, String t, String w*/){
        name = n;
        phone = p;
        address = a;
        email = e;
        /*facebook = f;
        instagram = i;
        twitter = t;
        wechat = w;*/
    }

    //getters
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

    // EFFECTS: shows a dialog to inform user that a contact is added
    public void createContactAddedMessage() {
        JOptionPane.showMessageDialog(this, "Contact added.", "Success!", JOptionPane.INFORMATION_MESSAGE, null);
    }
}
