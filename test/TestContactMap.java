import model.ContactMap;
import model.FavoriteContact;
import model.RegularContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;


public class TestContactMap {

    private ContactMap cMap;

    private FavoriteContact c1 = new FavoriteContact(
            "John Smith","911","1600 Pennsylvania Ave.","jsmith@gmail.com", true );
    private RegularContact c2 = new RegularContact(
            "Martin Garrix","1-604-111-9023","Mars","garrix99@yahoo.com", false);
    private RegularContact c3 = new RegularContact(
            "Stormzy","6789998212","Atlanta, Georgia","vossibop@gmail.com", false);



    @BeforeEach
    void beforeEachTest() {
        cMap = new ContactMap();
        cMap.add(c1);
        cMap.add(c2);
        cMap.add(c3);
    }

    @Test
    void testContactMapOperators() {
        assertEquals(3,cMap.size());
        assertFalse(cMap.isEmpty());
        assertTrue(cMap.contains(c1));
        assertEquals(c3,cMap.get("Stormy"));
    }

    @Test
    void testRun() throws IOException {
        String input = "6";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cMap.run();
    }

    @Test
    void testAddAContact() {
    }

    @Test
    void testFindAContact() {
    }

    @Test
    void testPrintFavorites() {
    }

    @Test
    void testNoContacts() {
    }

    @Test
    void testPrintContacts() {
    }

    @Test
    void testDeleteContact() {
    }









//    @Test
//    void testNoContacts() {
//        assertEquals(0,cMap.size());
//        assertTrue(cMap.isEmpty());
//        assertFalse(cMap.contains(contact1));
//
//        // testing addContact(), contacts is empty so with contact added, contact.length() should equal 1
//        cMap.add(contact1);
//        assertEquals(1,cMap.size());
//    }
//
//    @Test
//    void testMultiContacts() {
//        cMap.add(contact1);
//        cMap.add(contact2);
//        assertEquals(2, cMap.size());
//        assertFalse(cMap.isEmpty());
//        assertFalse(cMap.contains(contact3));
//
//        cMap.add(contact3);
//        assertEquals(3, cMap.size());
//        assertTrue(cMap.contains(contact3));
//
//
//    }
//
//    @Test
//    void testLoad() throws IOException {
//        List<String> lines = Files.readAllLines(Paths.get("loadtestfile.txt"));
//        for (String line : lines) {
//            ArrayList<String> partsOfLine = splitOnSpace(line);
//            String name = partsOfLine.get(0);
//            String phone = partsOfLine.get(1);
//            String address = partsOfLine.get(2);
//            String email = partsOfLine.get(3);
//            boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
//            RegularContact contact = new RegularContact(name, phone, address, email, favorite);
//            cMap.add(contact);
//        }
//    }

//    @Test
//    void testSave() throws IOException {
//        PrintWriter writer = new PrintWriter("savetestfile.txt");
//        contacts.add(contact1);
//        contacts.add(contact2);
//        for (Contact c : contacts) { // TODO: fix this bug!!!
//            writer.print(c.getName()    + "---");
//            writer.print(c.getPhone()   + "---");
//            writer.print(c.getAddress() + "---");
//            writer.print(c.getEmail()   + "---");
//            writer.print(c.getFavorite());
//            writer.println();
//        }
//        writer.close();
//        List<String> lines = Files.readAllLines(Paths.get("savetestfile.txt"));
//        List<RegularContact> contacts = new ArrayList<>();
//        for (String line : lines) {
//            ArrayList<String> partsOfLine = splitOnSpace(line);
//            String name = partsOfLine.get(0);
//            String phone = partsOfLine.get(1);
//            String address = partsOfLine.get(2);
//            String email = partsOfLine.get(3);
//            boolean favorite = Boolean.parseBoolean(partsOfLine.get(4));
//            RegularContact contact = new RegularContact(name, phone, address, email, favorite);
//            contacts.add(contact);
//        }
//        assertEquals("John Smith", contacts.get(0).getName());
//        assertEquals("911", contacts.get(0).getPhone());
//        assertEquals("1600 Pennsylvania Ave.", contacts.get(0).getAddress());
//        assertEquals("jsmith@gmail.com", contacts.get(0).getEmail());
//        assertTrue(contacts.get(0).getFavorite());
//        assertEquals("Martin Garrix", contacts.get(1).getName());
//        assertEquals("1-604-111-9023", contacts.get(1).getPhone());
//        assertEquals("Mars", contacts.get(1).getAddress());
//        assertEquals("garrix99@yahoo.com", contacts.get(1).getEmail());
//        assertFalse(contacts.get(1).getFavorite());
//    }

//    @Test
//    void testDoesContactExist() {
//        try {
//            cMap.doesContactExist(contact1);
//        } catch (ContactAlreadyExistsException e) {
//            fail("Exception should not have been thrown!");
//        }
//        cMap.add(contact1);
//        try {
//            cMap.doesContactExist(contact1);
//            fail("Exception should have been thrown!");
//        } catch (ContactAlreadyExistsException e) {
//            // expected
//        }
//    }

//    @Test
//    void testInvalidInputException() {
//        try {
//            contacts.checkInputName("Ricky");
//        } catch (InvalidInputException e) {
//            fail();
//        }
//        try {
//            contacts.checkInputName("Ricky9301");
//            fail();
//        } catch (InvalidInputException e) {
//            // expected
//        }
//
//        try {
//            contacts.checkInputEmail("mr.rickyma@gmail.com");
//        } catch (InvalidInputException e) {
//            fail();
//        }
//        try {
//            contacts.checkInputEmail("mr.rickymagmail");
//            fail();
//        } catch (InvalidInputException e) {
//            // expected
//        }
//
//        try {
//            contacts.checkInputPhone("(909)-569-9045");
//        } catch (InvalidInputException e) {
//            fail();
//        }
//        try {
//            contacts.checkInputPhone("604-asd-9031");
//            fail();
//        } catch (InvalidInputException e) {
//            // expected
//        }
//    }
}
