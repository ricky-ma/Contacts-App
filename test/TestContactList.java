import model.Contact;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;


public class TestContactList {

    private Contact contact;
    private Contact contact2;
    private ArrayList<Contact> contacts;

    @BeforeEach
    private void beforeEachTest() {
        contact = new Contact("Joe Smith", "911", "1600 Pennsylvania Ave.", "joesmith@gmail.com");
        contact2 = new Contact("John Doe", "1-800-888-1234", "Mars", "alien@yahoo.com");
        contacts = new ArrayList<>();
        contacts.add(contact);

    }



}
