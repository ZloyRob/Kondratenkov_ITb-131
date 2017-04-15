import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Main {
    private static final Logger log = LogManager.getLogger(Main.class.getName());
    public static void main(String[] args) {
        log.info("Приложение запущено");
        Flyway flyway = new Flyway();

        Connection dbConnection=null;
        flyway.setDataSource("jdbc:h2:file:./res/db/Vadim", "Vadim", "123");
        try {
            flyway.migrate();
        } catch (Exception e) {
            flyway.baseline();
        }

        UserInput userInput = new UserInput();
        Validator valid = new Validator();
        valid.allocation(userInput, args);

        try {
            dbConnection = getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery( String.format("SELECT * FROM USER WHERE (USER.LOGIN LIKE '%s')", userInput.login));
            result.next();
            User usbd = new User(result.getString("LOGIN"), result.getString("PASS"), Integer.valueOf(result.getString("ID")),result.getString("SALT"));
            boolean isAuthentication = valid.isAuthentication(usbd, userInput);
            String[] masOfPath = userInput.path.split("\\."); //разбиваем путь по уровням
            result = statement.executeQuery(String.format("SELECT * FROM RESOURCE where path like '%s", masOfPath[0])+"%'");
            ArrayList<Resource> resources = new ArrayList<>();
            while (result.next()) {
                resources.add(new Resource(result.getString("PATH"), Integer.valueOf(result.getString("USERID")),result.getString("ROLE"), Integer.valueOf(result.getString("ID"))));
            }
            boolean isAuthorization = valid.isAuthorization(resources, userInput, isAuthentication);
            Accounting journal = new Accounting();
            valid.isAccouting(journal, userInput, isAuthorization);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.execute(String.format("Insert into ACCOUNTING (DS, DE , VOL, RESID ) VALUES (PARSEDATETIME('%s','yyyy-MM-dd'), PARSEDATETIME('%s','yyyy-MM-dd'),'%s','%s')", sdf.format(journal.ds), sdf.format(journal.de), journal.vol, journal.resId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Работа приложения завершена");
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



