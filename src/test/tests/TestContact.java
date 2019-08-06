package tests;

import model.ContactMap;
import model.FavoriteContact;
import model.RegularContact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestContact {

    private RegularContact rc1;
    private RegularContact rc2;
    private RegularContact rc3;
    private RegularContact rc4;
    private FavoriteContact fc;
    private final String n = "John Smith";
    private final String p = "911";
    private final String a = "1600 Pennsylvania Ave. Washington DC";
    private final String e = "johnsmith@gmail.com";

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
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testSettersAndGetters() {
        fc.setName("John Smith");
        fc.setPhone("911");
        fc.setAddress("1600 Pennsylvania Ave. Washington DC");
        fc.setEmail("johnsmith@gmail.com");
        assertEquals(fc.getName(),fc.getName());
        assertEquals(fc.getPhone(),fc.getPhone());
        assertEquals(fc.getAddress(),fc.getAddress());
        assertEquals(fc.getEmail(),fc.getEmail());
        assertTrue(fc.getFavorite());

        rc1.setName("John Smith");
        rc1.setPhone("911");
        rc1.setAddress("1600 Pennsylvania Ave. Washington DC");
        rc1.setEmail("johnsmith@gmail.com");
        assertEquals(rc1.getName(),rc1.getName());
        assertEquals(rc1.getPhone(),rc1.getPhone());
        assertEquals(rc1.getAddress(),rc1.getAddress());
        assertEquals(rc1.getEmail(),rc1.getEmail());
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
    void testEditContactDetails() {
        String input = "Ricky Ma";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactDetails(rc1,1);

        String input2 = "(909)-569-9045";
        InputStream in2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(in2);
        rc1.editContactDetails(rc1,2);

        String input3 = "101 Testing Street, Vancouver BC";
        InputStream in3 = new ByteArrayInputStream(input3.getBytes());
        System.setIn(in3);
        rc1.editContactDetails(rc1,3);

        String input4 = "mr.rickyma@gmail.com";
        InputStream in4 = new ByteArrayInputStream(input4.getBytes());
        System.setIn(in4);
        rc1.editContactDetails(rc1,4);

        rc1.editContactDetails(rc1,5);
        assertTrue(rc1.getFavorite());
        rc1.editContactDetails(rc1,5);
        assertFalse(rc1.getFavorite());

        assertEquals("Ricky Ma", rc1.getName());
        assertEquals("(909)-569-9045", rc1.getPhone());
        assertEquals("mr.rickyma@gmail.com", rc1.getEmail());
        assertEquals("101 Testing Street, Vancouver BC", rc1.getAddress());
    }

    @Test
    void testEditContactDetailsInvalid() {
        String input = "12345";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rc1.editContactDetails(rc1,1);

        String input2 = "letters";
        InputStream in2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(in2);
        rc1.editContactDetails(rc1,2);

        String input3 = "invalidEmail321";
        InputStream in3 = new ByteArrayInputStream(input3.getBytes());
        System.setIn(in3);
        rc1.editContactDetails(rc1,4);


        assertEquals("John Smith", rc1.getName());
        assertEquals("911", rc1.getPhone());
        assertEquals("johnsmith@gmail.com", rc1.getEmail());
    }

    @SuppressWarnings({"ConstantConditions", "EqualsWithItself", "SimplifiableJUnitAssertion", "EqualsBetweenInconvertibleTypes"})
    @Test
    void testEquals() {
        ContactMap cMap = new ContactMap();
        RegularContact rc1a = new RegularContact(n, p, a, e, false);
        assertEquals(rc1,rc1a);
        assertNotEquals(rc1,rc2);

        assertTrue(rc1.equals(rc1));
        assertFalse(rc1.equals(null));
        assertFalse(rc1.equals(cMap));
    }

    @Test
    void testHashCode() {
        assertEquals(rc1.hashCode(), Objects.hash(n, p, a, e, false));
        assertNotEquals(rc1.hashCode(), Objects.hash(n, p, a, e, true));
    }
}
