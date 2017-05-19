import models.Resource;
import models.User;
import models.UserInput;
import org.junit.Before;
import org.junit.Test;
import services.Validator;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    private Validator validator;

    @Before
    public void def() {

        validator = new Validator();

    }

    @Test
    public void testEmptyUser() {
        UserInput userInput = new UserInput();
        assertEquals(true, userInput.isEmpty());
    }

    @Test
    public void testNotEmptyUser() {
        UserInput userInput = new UserInput("TEST","Pass");
        assertEquals(false, userInput.isEmpty());
    }

    @Test
    public void testHelp() {
        String[] args = {"-h"};
        assertEquals(true, validator.whatH(args));
    }

    @Test
    public void testNotHelp() {
        String[] args = {"-Not"};
        assertEquals(false, validator.whatH(args));
    }

    @Test
    public void testNotLog() {
        UserInput userInput = new UserInput("XXX","XXX");
        assertEquals(1, validator.isAuthenticationTest(null, userInput));

    }

    @Test
    public void testNotPass() {
        UserInput userInput = new UserInput("jdoe", "XXX");
        User jdoe = new User("0b6ae56859971bf3a34056e08b293b02", 1, "M5(ITYK");
        assertEquals(2, validator.isAuthenticationTest(jdoe, userInput));
    }

    @Test
    public void testLogAndPass() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ");
        User jdoe = new User("0b6ae56859971bf3a34056e08b293b02", 1, "M5(ITYK");
        assertEquals(0, validator.isAuthenticationTest(jdoe, userInput));
    }

    @Test
    public void testResAndRole() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a");
        Resource res = new Resource();
        assertEquals(0, validator.isAuthorizationTest(res, userInput));
    }

    @Test
    public void testChildRes() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b");
        Resource res = new Resource();
        assertEquals(0, validator.isAuthorizationTest(res, userInput));
    }

    @Test
    public void testNotRole() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","XXX","a.b");
        Resource res = new Resource();
        assertEquals(3, validator.isAuthorizationTest(res, userInput));
    }

    @Test
    public void testNotRes() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","XXX");
        assertEquals(4, validator.isAuthorizationTest(null, userInput));
    }

    @Test
    public void testResWhitBadRole() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","WRITE","a");
        assertEquals(4, validator.isAuthorizationTest(null, userInput));
    }

    @Test
    public void testNotResInDB() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","WRITE","a.bc");
        assertEquals(4, validator.isAuthorizationTest(null, userInput));
    }

    @Test
    public void testFullArg() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "2015-01-01", "2015-12-31", "100");
        assertEquals(0, validator.isAccoutingTest(userInput));
    }

    @Test
    public void testNotDe() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "01-01-2015", "2015-12-31", "100");
        assertEquals(5, validator.isAccoutingTest(userInput));
    }

    @Test
    public void testStringVol() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "2015-01-01", "2015-12-31", "XXX");
        assertEquals(5, validator.isAccoutingTest(userInput));
    }

    @Test
    public void testAllocation() {
        UserInput userInput = new UserInput();
        String[] args = ("-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100").split(" ");
        assertEquals("jdoe", validator.allocation(userInput, args).getLogin());
        assertEquals("sup3rpaZZ", validator.allocation(userInput, args).getPass());
        assertEquals("READ", validator.allocation(userInput, args).getRole());
        assertEquals("a.b", validator.allocation(userInput, args).getPath());
        assertEquals("2015-01-01", validator.allocation(userInput, args).getDss());
        assertEquals("2015-12-31", validator.allocation(userInput, args).getDes());
        assertEquals("100", validator.allocation(userInput, args).getVols());

    }


}
