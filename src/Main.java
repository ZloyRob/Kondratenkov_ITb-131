import org.flywaydb.core.Flyway;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Flyway flyway = new Flyway();
        Connection dbConnection=null;
        flyway.setDataSource("jdbc:h2:file:./res/db/Vadim", "Vadim", "123");
        try {
            flyway.migrate();
        } catch (Exception e) {
            flyway.baseline();
        }

        User johnDoe = new User("jdoe", "sup3rpaZZ", 7); //Создаем пользователей
        User janeRow = new User("jrow", "Qweqrty12", 9);
        ArrayList<User> users = new ArrayList<>(); //Создаем коллекцию пользователй
        users.add(johnDoe);
        users.add(janeRow);

        Resource res1 = new Resource("a", johnDoe.userId, "READ", 1); //Создаем ресурсы
        Resource res2 = new Resource("a.b", johnDoe.userId, "WRITE", 2);
        Resource res3 = new Resource("a.b.c", janeRow.userId, "EXECUTE", 3);
        Resource res4 = new Resource("a.bc", johnDoe.userId, "EXECUTE", 4);
        ArrayList<Resource> resources = new ArrayList<>(); //Создаем коллекцию ресурсов
        resources.add(res1);
        resources.add(res2);
        resources.add(res3);
        resources.add(res4);

        ArrayList<Accounting> journal = new ArrayList<>(); //список всех сессий
        UserInput userInput = new UserInput();
        Validator valid = new Validator();
        valid.allocation(userInput, args);
        try {
            dbConnection = getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM USER");
            while (result.next()) {
                System.out.println(result.getString("ID") + " " + result.getString("LOGIN") + " " + result.getString("PASS") + " " + result.getString("SALT"));
            }
            result = statement.executeQuery("SELECT * FROM RESOURCE");
            while (result.next()) {
                System.out.println(result.getString("USERID") + " " + result.getString("PATH") + " " + result.getString("ROLE"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {

                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            //Connection dbConnection = getDBConnection();
            boolean isAuthentication = valid.isAuthentication(users, userInput);
            boolean isAuthorization = valid.isAuthorization(resources, userInput, isAuthentication);
            valid.isAccouting(journal, userInput, isAuthorization);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:h2:file:./res/db/Vadim", "Vadim", "123");
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

}



