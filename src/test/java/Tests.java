import DAO.DaoClass;
import Models.Resource;
import Models.User;
import Models.UserInput;
import Services.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Tests {

    private Validator validator;
    private UserInput userInput;
    private User jdoe;
    private DaoClass dao;
    private Resource res;

    @Before
    public void def() {
        jdoe = new User("0b6ae56859971bf3a34056e08b293b02", 1, "M5(ITYK");
        dao = mock(DaoClass.class);
        res = new Resource();
    }

    @Test
    public void test1() {
        userInput = new UserInput();
        assertEquals(true, userInput.isEmpty());
    }

    @Test
    public void test2() {
        validator = new Validator();
        String[] args = {"-h"};
        assertEquals(true, validator.whatH(args));
    }

    @Test
    public void test3() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.login = "XXX";
        userInput.pass = "XXX";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(null);
        assertEquals(1, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));

    }

    @Test
    public void test4() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.login = "jdoe";
        userInput.pass = "XXX";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(jdoe);
        assertEquals(2, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));
    }

    @Test
    public void test5() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.login = "jdoe";
        userInput.pass = "sup3rpaZZ";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(jdoe);
        assertEquals(0, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));
    }

    @Test
    public void test6() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.role = "READ";
        userInput.path = "a";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(res);
        assertEquals(0, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test7() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.role = "READ";
        userInput.path = "a.b";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(res);
        assertEquals(0, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test8() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.role = "XXX";
        userInput.path = "a.b";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(res);
        assertEquals(3, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test9() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.role = "READ";
        userInput.path = "XXX";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(null);
        assertEquals(4, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test10() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.role = "WRITE";
        userInput.path = "a";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(null);
        assertEquals(4, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test11() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.role = "WRITE";
        userInput.path = "a.bc";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(null);
        assertEquals(4, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test12() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.dss = "2015-01-01";
        userInput.des = "2015-12-31";
        userInput.vols = "100";
        assertEquals(0, validator.isAccoutingTest(userInput));
    }

    @Test
    public void test13() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.dss = "01-01-2015";
        userInput.des = "2015-12-31";
        userInput.vols = "100";
        assertEquals(5, validator.isAccoutingTest(userInput));
    }

    @Test
    public void test14() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.dss = "2015-01-01";
        userInput.des = "2015-12-31";
        userInput.vols = "XXX";
        assertEquals(5, validator.isAccoutingTest(userInput));
    }

    @Test
    public void test15() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.login = "X";
        userInput.pass = "X";
        DaoClass dao = mock(DaoClass.class);
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(null);
        assertEquals(1, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));

    }

    @Test
    public void test16() {
        userInput = new UserInput();
        validator = new Validator();
        userInput.login = "X";
        userInput.pass = "X";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(null);
        assertEquals(1, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));

    }
}
