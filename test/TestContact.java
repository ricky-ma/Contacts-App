import model.FavoriteContact;
import model.RegularContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestContact {

    private RegularContact rc;
    private FavoriteContact fc;
    private String n;
    private String p;
    private String a;
    private String e;
    private boolean f;

    @BeforeEach
    void beforeEachTest() {
        rc = new RegularContact(n, p, a, e, false);
        fc = new FavoriteContact(n, p, a, e , true);
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

        rc.setName("John Smith");
        rc.setPhone("911");
        rc.setAddress("1600 Pennsylvania Ave. Washington DC");
        rc.setEmail("johnsmith@gmail.com");
        assertEquals(rc.getName(),rc.name);
        assertEquals(rc.getPhone(),rc.phone);
        assertEquals(rc.getAddress(),rc.address);
        assertEquals(rc.getEmail(),rc.email);
        assertFalse(rc.getFavorite());
    }
}
