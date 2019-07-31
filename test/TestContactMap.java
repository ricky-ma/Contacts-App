import model.ContactAlreadyExistsException;
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
    private RegularContact c4 = new RegularContact(
            "Test Name","1234567890","193 Testing Rd.","testing@yahoo.com", false);



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
        assertEquals(c3,cMap.get("Stormzy"));
    }

    @Test
    void testRun() throws IOException {
        String input = "6";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cMap.run();
    }

//    @Test
//    void testAddAContact() {
//        String input = "Test Name\n1234567890\n101 Testing Street\ntesting@gmail.com\nfalse";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        cMap.addNewContact();
//    }

    @Test
    void testFindAContact() {
    }

    @Test
    void testEditAContact() {

    }

    @Test
    void testPrintFavorites() {
    }


    @Test
    void testPrintContacts() {
        assertTrue(cMap.printAllContacts());
    }

    @Test
    void testDeleteContact() {
    }

    @Test
    void testFavoriteOrRegular() {
        assertTrue(ContactMap.favoriteOrRegular(1,"name","123","address","m@.com").favorite);
        assertFalse(ContactMap.favoriteOrRegular(2,"name","123","address","m@.com").favorite);

        assertNull(ContactMap.favoriteOrRegular(1,"invalid123","123","address","m@.com"));
        assertNull(ContactMap.favoriteOrRegular(2,"invalid123","123","address","m@.com"));
    }

    @Test
    void testDoesContactExist() {
        try {
            cMap.doesContactExist(c4);
        } catch (ContactAlreadyExistsException e) {
            fail("Exception should not have been thrown!");
        }
        cMap.add(c4);
        try {
            cMap.doesContactExist(c4);
            fail("Exception should have been thrown!");
        } catch (ContactAlreadyExistsException e) {
            // expected
        }
    }
}
