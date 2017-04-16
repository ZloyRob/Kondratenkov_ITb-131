
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class DaoClass {
    Connection getDBConnection() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            return DriverManager.getConnection("jdbc:h2:file:./res/db/Vadim", "Vadim", "123");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    void closeConnection(Connection dbConnection) {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            // logger.debug(e.getMessage());
        }
    }

    User getUserFromDataBase(UserInput userInput, Connection dbConnection) {
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM USER WHERE (USER.LOGIN LIKE '%s')", userInput.login));
            result.next();
            return new User(result.getString("LOGIN"), result.getString("PASS"), Integer.valueOf(result.getString("ID")), result.getString("SALT"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User();
    }

    ArrayList<Resource> getResourceFromBase(UserInput userInput, Connection dbConnection) {
        try {
            Statement statement = dbConnection.createStatement();
            String[] masOfPath = userInput.path.split("\\."); //разбиваем путь по уровням
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM RESOURCE where path like '%s", masOfPath[0]) + "%'");
            ArrayList<Resource> resources = new ArrayList<>();
            while (result.next()) {
                resources.add(new Resource(result.getString("PATH"), Integer.valueOf(result.getString("USERID")), result.getString("ROLE"), Integer.valueOf(result.getString("ID"))));
            }
            return resources;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    void insertRecordIntoDataBase(Connection dbConnection, Accounting journal) {
        try {
            Statement statement = dbConnection.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.execute(String.format("Insert into ACCOUNTING (DS, DE , VOL, RESID ) VALUES (PARSEDATETIME('%s','yyyy-MM-dd'), PARSEDATETIME('%s','yyyy-MM-dd'),'%s','%s')", sdf.format(journal.ds), sdf.format(journal.de), journal.vol, journal.resId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
