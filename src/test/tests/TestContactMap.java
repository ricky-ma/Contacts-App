package tests;

import model.Contact;
import model.ContactMap;
import model.FavoriteContact;
import model.RegularContact;
import model.exceptions.ContactAlreadyExistsException;
import model.interfaces.ContactMapObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class TestContactMap implements ContactMapObserver {

    private ContactMap cMap;
    private Map<String, Contact> testContactMap;
    private Map<String, Contact> testFavoritesMap;

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
        cMap.addObserver(this);
    }

    @Test
    void testGetContactMap() {
        cMap.setContactMap(testContactMap);
        assertEquals(testContactMap, cMap.getContactMap());
        cMap.setFavoritesMap(testFavoritesMap);
        assertEquals(testFavoritesMap, cMap.getFavoritesMap());
    }


    @Test
    void testContactMapOperators() {
        cMap.add(c1);
        cMap.add(c2);
        cMap.add(c3);
        assertEquals(3,cMap.size());
        assertFalse(cMap.isEmpty());
        assertTrue(cMap.contains(c1));
        assertEquals(c3,cMap.get("Stormzy"));
    }


    @Test
    void testAddAContact() {
        try {
            cMap.addNewContact("John Smith---911---1600 Pennsylvania Ave.---jsmith@gmail.com---true");
        } catch (ContactAlreadyExistsException e) {
            fail();
        }
        assertTrue(cMap.contains(cMap.get("John Smith")));
        try {
            cMap.addNewContact("John Smith---911---1600 Pennsylvania Ave.---jsmith@gmail.com---true");
        } catch (ContactAlreadyExistsException e) {
            // expected
        }
    }

    @Test
    void testDeleteContact() {
        assertFalse(cMap.deleteContact("John Smith"));
        assertFalse(cMap.deleteContact("Martin Garrix"));

        cMap.add(c1);
        cMap.add(c2);
        assertTrue(cMap.deleteContact("John Smith"));
        assertTrue(cMap.deleteContact("Martin Garrix"));
        assertFalse(cMap.contains(c1));
        assertFalse(cMap.contains(c2));
    }


    @Test
    void testEditContact() {
        cMap.add(c1);
        cMap.editContact("John Smith---9095699045---1600 Pennsylvania Ave.---jsmith@gmail.com---true");
        assertEquals("9095699045", cMap.get("John Smith").getPhone());

        cMap.add(c2);
        cMap.editContact("Martin Garrix---1-604-111-9023---Mars---jsmith@gmail.com---false");
        assertEquals("jsmith@gmail.com", cMap.get("Martin Garrix").getEmail());
        cMap.editContact("Martin Garrix---18001234567---Mars---jsmith@gmail.com---true");
        assertEquals("18001234567", cMap.get("Martin Garrix").getPhone());
        assertTrue(cMap.get("Martin Garrix").getFavorite());
    }


    @Test
    void testLoad() throws IOException {
        Contact test1 = new FavoriteContact("Test Name1","604-707-9090",
                "101 Testing Dr.","testing@gmail.com", true);
        Contact test2 = new RegularContact("Test Name2","604-707-9090",
                "101 Testing Dr.","testing@gmail.com", false);
        Contact test3 = new FavoriteContact("Test Name3","604-707-9090",
                "101 Testing Dr.","testing@gmail.com", true);
        Contact test4 = new RegularContact("Test Name4","604-707-9090",
                "101 Testing Dr.","testing@gmail.com", false);

        cMap.load("testfileload.txt");
        assertTrue(cMap.contains(test1));
        assertTrue(cMap.contains(test2));
        assertTrue(cMap.contains(test3));
        assertTrue(cMap.contains(test4));
    }


    @Test
    void testSave() throws IOException {
        String path = "C:\\Users\\mrric\\IdeaProjects\\project_rickyma\\testfilesave.txt";
        Contact test1 = new FavoriteContact("Test Name1","604-707-9090",
                "101 Testing Dr.","testing@gmail.com",true);
        Contact test2 = new RegularContact("Test Name2","604-707-9090",
                "101 Testing Dr.","testing@gmail.com",false);
        Contact test3 = new FavoriteContact("Test Name3","604-707-9090",
                "101 Testing Dr.","testing@gmail.com",true);
        Contact test4 = new RegularContact("Test Name4","604-707-9090",
                "101 Testing Dr.","testing@gmail.com",false);
        File file = new File("C:\\Users\\mrric\\IdeaProjects\\project_rickyma\\testfilesave.txt");

        cMap.add(test1);
        cMap.add(test2);
        cMap.add(test3);
        cMap.add(test4);
        assertEquals(4, cMap.size());


        cMap.save("testfilesave.txt");
        String content = new String(Files.readAllBytes(Paths.get(path)));
        assertTrue(file.length() > 0);
        assertTrue(content.contains("Test Name1---604-707-9090---101 Testing Dr.---testing@gmail.com---true"));
        assertTrue(content.contains("Test Name2---604-707-9090---101 Testing Dr.---testing@gmail.com---false"));
        assertTrue(content.contains("Test Name3---604-707-9090---101 Testing Dr.---testing@gmail.com---true"));
        assertTrue(content.contains("Test Name4---604-707-9090---101 Testing Dr.---testing@gmail.com---false"));
    }

    @Test
    void testObserver() {

    }

    public void updateModel(String name) {
    }
}
