import model.RegularContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static model.ContactList.splitOnSpace;
import static org.junit.jupiter.api.Assertions.*;


public class TestContactList {

    private RegularContact contact1 = new RegularContact(
            "John Smith","911","1600 Pennsylvania Ave.","jsmith@gmail.com", true );
    private RegularContact contact2 = new RegularContact(
            "Martin Garrix","1-604-111-9023","Mars","garrix99@yahoo.com", false);
    private RegularContact contact3 = new RegularContact(
            "Stormzy","6789998212","Atlanta, Georgia","vossibop@gmail.com", false);

    private List<RegularContact> contacts = new ArrayList<>();

    @BeforeEach
    private void beforeEachTest() throws IOException {
        List<RegularContact> contacts = new ArrayList<>();
    }

    @Test
    void testNoContacts() {
        assertEquals(0,contacts.size());
        assertTrue(contacts.isEmpty());
        assertFalse(contacts.contains(contact1));

        // testing addContact(), contacts is empty so with contact added, contact.length() should equal 1
        contacts.add(contact1);
        assertEquals(1,contacts.size());
    }

    @Test
    void testMultiContacts() {
        contacts.add(contact1);
        contacts.add(contact2);
        assertEquals(2, contacts.size());
        assertFalse(contacts.isEmpty());
        assertFalse(contacts.contains(contact3));

        contacts.add(contact3);
        assertEquals(3, contacts.size());
        assertTrue(contacts.contains(contact3));


    }

    @Test
    void testLoad() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("loadtestfile.txt"));
        int counter = 0;
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String name = partsOfLine.get(0);
            String phone = partsOfLine.get(1);
            String address = partsOfLine.get(2);
            String email = partsOfLine.get(3);
            boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
            RegularContact contact = new RegularContact(name, phone, address, email, favorite);
            contacts.add(contact);
        }
    }

    @Test
    void testSave() throws IOException {
        PrintWriter writer = new PrintWriter("savetestfile.txt");
        contacts.add(contact1);
        contacts.add(contact2);
        for (RegularContact c : contacts) {
            writer.print(c.getName()    + "---");
            writer.print(c.getPhone()   + "---");
            writer.print(c.getAddress() + "---");
            writer.print(c.getEmail());
            writer.println();
        }
        writer.close();
        List<String> lines = Files.readAllLines(Paths.get("savetestfile.txt"));
        List<RegularContact> contacts = new ArrayList<>();
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String name = partsOfLine.get(0);
            String phone = partsOfLine.get(1);
            String address = partsOfLine.get(2);
            String email = partsOfLine.get(3);
            boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
            RegularContact contact = new RegularContact(name, phone, address, email, favorite);
            contacts.add(contact);
        }
        assertEquals("John Smith", contacts.get(0).getName());
        assertEquals("911", contacts.get(0).getPhone());
        assertEquals("1600 Pennsylvania Ave.", contacts.get(0).getAddress());
        assertEquals("jsmith@gmail.com", contacts.get(0).getEmail());
        assertEquals("Martin Garrix", contacts.get(1).getName());
        assertEquals("1-604-111-9023", contacts.get(1).getPhone());
        assertEquals("Mars", contacts.get(1).getAddress());
        assertEquals("garrix99@yahoo.com", contacts.get(1).getEmail());
    }

//    @Test
//    void testNewFavoriteContact() {
//        try {
//
//        } catch (ContactAlreadyExistsException e) {
//
//        }
//        contacts.add(contact1);
//
//    }
}
