import model.Contact;
import model.FavoriteContact;
import model.RegularContact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestContact {

    private RegularContact rc1;
    private RegularContact rc2;
    private RegularContact rc3;
    private RegularContact rc4;
    private FavoriteContact fc;
    private String n = "John Smith";
    private String p = "911";
    private String a = "1600 Pennsylvania Ave. Washington DC";
    private String e = "johnsmith@gmail.com";

    private List<Contact> favorites;
    private List<Contact> contacts;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void beforeEachTest() {
        rc1 = new RegularContact(n, p, a, e, false);
        rc2 = new RegularContact("John1389", "9095699045",
                "101 Test Address", "js@gmail.com", false);
        rc3 = new RegularContact("John1389", "letters",
                "101 Test Address", "js@gmail.com", false);
        rc4 = new RegularContact("John1389", "9095699045",
                "101 Test Address", "notAValidEmail", false);
        fc = new FavoriteContact(n, p, a, e , true);

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testSettersAndGetters() {
        fc.setName("John Smith");
        fc.setPhone("911");
        fc.setAddress("1600 Pennsylvania Ave. Washington DC");
        fc.setEmail("johnsmith@gmail.com");
        assertEquals(fc.getName(),fc.name);
        assertEquals(fc.getPhone(),fc.phone);
        assertEquals(fc.getAddress(),fc.address);
        assertEquals(fc.getEmail(),fc.email);
        assertTrue(fc.getFavorite());

        rc1.setName("John Smith");
        rc1.setPhone("911");
        rc1.setAddress("1600 Pennsylvania Ave. Washington DC");
        rc1.setEmail("johnsmith@gmail.com");
        assertEquals(rc1.getName(),rc1.name);
        assertEquals(rc1.getPhone(),rc1.phone);
        assertEquals(rc1.getAddress(),rc1.address);
        assertEquals(rc1.getEmail(),rc1.email);
        assertFalse(rc1.getFavorite());
    }

    @Test
    void testCheckInput() {
        assertTrue(rc1.checkInput());
        assertFalse(rc2.checkInput());
        assertFalse(rc3.checkInput());
        assertFalse(rc4.checkInput());
    }

    @Test
    void testEditContactValidName() {
        String input = "Ricky Ma";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactName(rc1);

        assertEquals("Ricky Ma", rc1.name);
    }

    @Test
    void testEditContactInvalidName() {
        String input = "12345";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactName(rc1);

        assertEquals("John Smith", rc1.name);
    }

    @Test
    void testEditContactValidPhone() {
        String input = "909-569-9045";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactPhone(rc1);

        assertEquals("909-569-9045", rc1.phone);
    }

    @Test
    void testEditContactInvalidPhone() {
        String input = "letters";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactPhone(rc1);

        String out = outContent.toString();

        assertEquals("911", rc1.phone);
    }

    @Test
    void testEditContactValidEmail() {
        String input = "mr.rickyma@gmail.com";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactEmail(rc1);

        assertEquals("mr.rickyma@gmail.com", rc1.email);
    }

    @Test
    void testEditContactInvalidEmail() {
        String input = "invalidEmail321";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactEmail(rc1);

        String out = outContent.toString();

        assertEquals("johnsmith@gmail.com", rc1.email);
    }

    @Test
    void testEditContactAddress() {
        String input = "101 Testing Street, Vancouver BC";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactAddress(rc1);

        assertEquals("101 Testing Street, Vancouver BC", rc1.address);
    }

    @Test
    void testEditContactFavorite() {
        assertFalse(rc2.favorite);
        rc2.editContactFavorite(rc2);
        assertTrue(rc2.favorite);

        assertTrue(fc.favorite);
        fc.editContactFavorite(fc);
        assertFalse(fc.favorite);
    }

    @Test
    void testEquals() {
        assertTrue(rc1.equals(rc1));
        assertFalse(rc1.equals(rc2));
        // TODO: get full coverage
    }
}
