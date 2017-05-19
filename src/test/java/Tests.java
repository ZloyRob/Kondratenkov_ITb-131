import dao.DaoClass;
import models.Resource;
import models.User;
import models.UserInput;
import services.*;
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
    private AAAService aaaService;
    private UserInput userInput2;

    @Before
    public void def() {
        jdoe = new User("0b6ae56859971bf3a34056e08b293b02", 1, "M5(ITYK");
        dao = mock(DaoClass.class);
        res = new Resource();
        aaaService = new AAAService();
        validator = new Validator();
        userInput = new UserInput();
    }

    @Test
    public void test1() {
        assertEquals(true, userInput.isEmpty());
    }

    @Test
    public void test2() {
        String[] args = {"-h"};
        assertEquals(true, validator.whatH(args));
    }

    @Test
    public void test3() {
        userInput.login = "XXX";
        userInput.pass = "XXX";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(null);
        assertEquals(1, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));

    }

    @Test
    public void test4() {
        userInput.login = "jdoe";
        userInput.pass = "XXX";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(jdoe);
        assertEquals(2, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));
    }

    @Test
    public void test5() {
        userInput.login = "jdoe";
        userInput.pass = "sup3rpaZZ";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(jdoe);
        assertEquals(0, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));
    }

    @Test
    public void test6() {
        userInput.role = "READ";
        userInput.path = "a";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(res);
        assertEquals(0, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test7() {
        userInput.role = "READ";
        userInput.path = "a.b";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(res);
        assertEquals(0, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test8() {
        userInput.role = "XXX";
        userInput.path = "a.b";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(res);
        assertEquals(3, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test9() {
        userInput.role = "READ";
        userInput.path = "XXX";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(null);
        assertEquals(4, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test10() {
        userInput.role = "WRITE";
        userInput.path = "a";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(null);
        assertEquals(4, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test11() {
        userInput.role = "WRITE";
        userInput.path = "a.bc";
        when(dao.getResourceFromBase(userInput, null)).thenReturn(null);
        assertEquals(4, validator.isAuthorizationTest(dao.getResourceFromBase(userInput, null), userInput));
    }

    @Test
    public void test12() {
        userInput.dss = "2015-01-01";
        userInput.des = "2015-12-31";
        userInput.vols = "100";
        assertEquals(0, validator.isAccoutingTest(userInput));
    }

    @Test
    public void test13() {
        userInput.dss = "01-01-2015";
        userInput.des = "2015-12-31";
        userInput.vols = "100";
        assertEquals(5, validator.isAccoutingTest(userInput));
    }

    @Test
    public void test14() {
        userInput.dss = "2015-01-01";
        userInput.des = "2015-12-31";
        userInput.vols = "XXX";
        assertEquals(5, validator.isAccoutingTest(userInput));
    }

    @Test
    public void test15() {
        userInput.login = "X";
        userInput.pass = "X";
        DaoClass dao = mock(DaoClass.class);
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(null);
        assertEquals(1, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));

    }

    @Test
    public void test16() {
        userInput.login = "X";
        userInput.pass = "X";
        when(dao.getUserFromDataBase(userInput, null)).thenReturn(null);
        assertEquals(1, validator.isAuthenticationTest(dao.getUserFromDataBase(userInput, null), userInput));
    }

    @Test
    public void test17() {
        String[] args = ("-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100").split(" ");
        userInput.login = "jdoe";
        userInput.pass = "sup3rpaZZ";
        userInput.role = "READ";
        userInput.path = "a.b";
        userInput.dss = "2015-01-01";
        userInput.des = "2015-12-31";
        userInput.vols = "100";
        userInput2 = new UserInput();
        assertEquals(userInput.login, validator.allocation(userInput2, args).login);
        assertEquals(userInput.pass, validator.allocation(userInput2, args).pass);
        assertEquals(userInput.role, validator.allocation(userInput2, args).role);
        assertEquals(userInput.path, validator.allocation(userInput2, args).path);
        assertEquals(userInput.dss, validator.allocation(userInput2, args).dss);
        assertEquals(userInput.des, validator.allocation(userInput2, args).des);
        assertEquals(userInput.vols, validator.allocation(userInput2, args).vols);

    }

    @Test
    public void aaaTest1(){
        userInput.pass = "sup3rpaZZ";
        userInput.userId = 1;
        assertEquals(true, aaaService.isCheckPass(jdoe, userInput));
    }
    @Test
    public void aaaTest2(){
        userInput.pass = "XXX";
        userInput.userId = 1;
        assertEquals(false, aaaService.isCheckPass(jdoe, userInput));
    }

    @Test
    public void aaaTest3(){
        userInput.role = "READ";
        assertEquals(true, aaaService.isCheckRole(userInput));
    }

    @Test
    public void aaaTest4(){
        userInput.role = "XXX";
        assertEquals(false, aaaService.isCheckRole(userInput));
    }

    @Test
    public void aaaTest5(){
        userInput.dss = "2015-01-01";
        userInput.des = "2015-12-31";
        assertEquals(true, aaaService.isCheckDate(userInput));
    }

    @Test
    public void aaaTest6(){
        userInput.dss = "01-01-2015";
        userInput.des = "2015-12-31";
        assertEquals(false, aaaService.isCheckDate(userInput));
    }

    @Test
    public void aaaTest7(){
        userInput.vols="100";
        assertEquals(true, aaaService.isCheckVol(userInput));
    }

    @Test
    public void aaaTest8(){
        userInput.vols="-100";
        assertEquals(false, aaaService.isCheckVol(userInput));
    }

    @Test
    public void aaaTest9(){
        userInput.vols="ПРИВЕТ";
        assertEquals(false, aaaService.isCheckVol(userInput));
    }

}
