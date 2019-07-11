import model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestContact {

    private Contact c;
    private String n;
    private String p;
    private String a;
    private String e;

    @BeforeEach
    void beforeEachTest() {
        c = new Contact(n, p, a, e);
    }

    @Test
    void testSettersAndGetters() {
        c.setName("John Smith");
        c.setPhone("911");
        c.setAddress("1600 Pennsylvania Ave. Washington DC");
        c.setEmail("johnsmith@gmail.com");
        assertEquals(c.getName(),c.name);
        assertEquals(c.getPhone(),c.phone);
        assertEquals(c.getAddress(),c.address);
        assertEquals(c.getEmail(),c.email);
    }



}
