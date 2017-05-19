import models.User;
import models.UserInput;
import org.junit.Before;
import org.junit.Test;
import services.AAAService;

import static org.junit.Assert.assertEquals;

public class AAAServiceTest {

    private AAAService aaaService;

    @Before
    public void def() {
        aaaService = new AAAService();
    }

    @Test
    public void testCheckPass() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ");
        userInput.userId = 1;
        User jdoe = new User("0b6ae56859971bf3a34056e08b293b02", 1, "M5(ITYK");
        assertEquals(true, aaaService.isCheckPass(jdoe, userInput));
    }

    @Test
    public void testCheckWrongPass() {
        UserInput userInput = new UserInput("jdoe", "XXX");
        userInput.userId = 1;
        User jdoe = new User("0b6ae56859971bf3a34056e08b293b02", 1, "M5(ITYK");
        assertEquals(false, aaaService.isCheckPass(jdoe, userInput));
    }

    @Test
    public void testCheckRole() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a");
        assertEquals(true, aaaService.isCheckRole(userInput));
    }

    @Test
    public void testCheckWrongRole() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","XXX","a");
        assertEquals(false, aaaService.isCheckRole(userInput));
    }

    @Test
    public void testCheckDate() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "2015-01-01", "2015-12-31", "100");
        assertEquals(true, aaaService.isCheckDate(userInput));
    }

    @Test
    public void testCheckWrongDate() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "01-01-2015", "2015-12-31", "100");
        assertEquals(false, aaaService.isCheckDate(userInput));
    }

    @Test
    public void testCheckVol() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "2015-01-01", "2015-12-31", "100");
        assertEquals(true, aaaService.isCheckVol(userInput));
    }

    @Test
    public void testCheckWrongVol() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "2015-01-01", "2015-12-31", "-100");
        assertEquals(false, aaaService.isCheckVol(userInput));
    }

    @Test
    public void testCheckStringVol() {
        UserInput userInput = new UserInput("jdoe","sup3rpaZZ","READ","a.b", "2015-01-01", "2015-12-31", "Test");
        assertEquals(false, aaaService.isCheckVol(userInput));
    }

}

