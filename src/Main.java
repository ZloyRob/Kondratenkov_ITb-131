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


        ArrayList<User> users = new ArrayList<>(); //Создаем коллекцию пользователй

        ArrayList<Resource> resources = new ArrayList<>(); //Создаем коллекцию ресурсов


        ArrayList<Accounting> journal = new ArrayList<>(); //список всех сессий

        UserInput userInput = new UserInput();
        Validator valid = new Validator();
        valid.allocation(userInput, args);
        try {
            dbConnection = getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM USER");
            while (result.next()) {
                users.add(new User(result.getString("LOGIN"), result.getString("PASS"), Integer.valueOf(result.getString("ID")),result.getString("SALT")));
                //System.out.println(result.getString("ID") + " " + result.getString("LOGIN") + " " + result.getString("PASS") + " " + result.getString("SALT"));
            }
            result = statement.executeQuery("SELECT * FROM RESOURCE");
            while (result.next()) {
                resources.add(new Resource(result.getString("PATH"), Integer.valueOf(result.getString("USERID")),result.getString("ROLE"), Integer.valueOf(result.getString("ID"))));
                //System.out.println(result.getString("USERID") + " " + result.getString("PATH") + " " + result.getString("ROLE"));
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
            dbConnection = getDBConnection();

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



