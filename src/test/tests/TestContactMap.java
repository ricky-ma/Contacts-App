package tests;

import model.Contact;
import model.ContactMap;
import model.FavoriteContact;
import model.RegularContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        cMap.addNewContact
                ("John Smith---911---1600 Pennsylvania Ave.---jsmith@gmail.com---true");
        assertTrue(cMap.contains(c1));
        assertFalse(cMap.addNewContact
                ("John Smith---911---1600 Pennsylvania Ave.---jsmith@gmail.com---true"));

        assertFalse(cMap.contains(c4));
        cMap.addNewContact
                ("Test Name---1234567890---193 Testing Rd.---testing@yahoo.com---false");
        assertTrue(cMap.contains(c1));
        assertFalse(cMap.addNewContact
                ("Test Name---1234567890---193 Testing Rd.---testing@yahoo.com---false"));
    }


    @Test
    void testFindAContact() {
        assertFalse(cMap.findContact("John Smith"));
        cMap.add(c1);
        assertTrue(cMap.findContact("John Smith"));
    }


    @Test
    void testPrintFavorites() {
        assertTrue(cMap.printFavorites());
    }


    @Test
    void testPrintContacts() {
        assertFalse(cMap.printAllContacts());

        cMap.add(c1);
        cMap.add(c2);
        cMap.add(c3);
        assertTrue(cMap.printAllContacts());
    }


    @Test
    void testDeleteContact() {
        assertFalse(cMap.deleteContact("John Smith"));

        cMap.add(c1);
        assertTrue(cMap.deleteContact("John Smith"));
        assertFalse(cMap.contains(c1));
    }


    @Test
    void testEditContact() {
        assertNull(cMap.editContact("John Smith"));
        cMap.add(c1);
        assertEquals(c1,cMap.editContact("John Smith"));
    }


    @Test
    void testLoad() throws IOException {
        Contact test1 = new RegularContact("Test Name1","604-707-9090",
                "101 Testing Dr.","testing@gmail.com", true);
        Contact test2 = new RegularContact("Test Name2","604-707-9090",
                "101 Testing Dr.","testing@gmail.com", false);
        Contact test3 = new RegularContact("Test Name3","604-707-9090",
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
        String expectedContent =
                "Test Name1---604-707-9090---101 Testing Dr.---testing@gmail.com---true\n" +
                "Test Name2---604-707-9090---101 Testing Dr.---testing@gmail.com---false\n" +
                "Test Name3---604-707-9090---101 Testing Dr.---testing@gmail.com---true\n" +
                "Test Name4---604-707-9090---101 Testing Dr.---testing@gmail.com---false";
        String actualContent = readAllBytesJava7(path);

        File file = new File("C:\\Users\\mrric\\IdeaProjects\\project_rickyma\\testfilesave.txt");

        cMap.addNewContact("Test Name1---604-707-9090---101 Testing Dr.---testing@gmail.com---true");
        cMap.addNewContact("Test Name2---604-707-9090---101 Testing Dr.---testing@gmail.com---false");
        cMap.addNewContact("Test Name3---604-707-9090---101 Testing Dr.---testing@gmail.com---true");
        cMap.addNewContact("Test Name4---604-707-9090---101 Testing Dr.---testing@gmail.com---false");
        assertEquals(4, cMap.size());


        cMap.save("testfilesave.txt");
        assertTrue(file.length() > 0);
        assertEquals(expectedContent, actualContent);
    }

    private static String readAllBytesJava7(String filePath)
    {
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
}
