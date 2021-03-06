package dao;

import models.Accounting;
import models.Resource;
import models.User;
import models.UserInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DaoClass {
    private static final Logger log = LogManager.getLogger(DaoClass.class.getName());

    public Connection getDBConnection() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            log.debug(e);
        }
        try {
            return DriverManager.getConnection("jdbc:h2:file:./res/db/Vadim", "Vadim", "123");

        } catch (SQLException e) {
            log.debug(e);
            return null;
        }
    }

    public void closeConnection(Connection dbConnection) {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            log.debug(e);
        }
    }

    public User getUserFromDataBase(UserInput userInput, Connection dbConnection) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM USER WHERE (USER.LOGIN LIKE ?)");
            statement.setString(1, userInput.getLogin());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                log.info("Пользователь найден");
                return new User(result.getString("PASS"), Integer.valueOf(result.getString("ID")), result.getString("SALT"));
            }
        } catch (SQLException e) {
            log.debug(e);
        }
        log.error("Пользователь {} не найден", userInput.getLogin());
        return null;
    }

    public Resource getResourceFromBase(UserInput userInput, Connection dbConnection) {
        try {
            PreparedStatement statement;
            ResultSet result;
            if (userInput.getPath() == null) {
                return new Resource();
            }
            String[] masOfPath = userInput.getPath().split("\\."); //разбиваем путь по уровням
            boolean access = false;
            String findPath = "";
            for (String string : masOfPath) {
                findPath += string; //опускаемся на уровень ниже
                {
                    statement = dbConnection.prepareStatement("SELECT * FROM RESOURCE where (RESOURCE.PATH like ?) and (RESOURCE.ROLE like ?) and (RESOURCE.USERID like ?)");
                    statement.setString(1, findPath);
                    statement.setString(2, userInput.getRole());
                    statement.setInt(3, userInput.userId);
                    result = statement.executeQuery();
                    if (result.next()) { //проверяем вернулся ли хоть 1 ресурс с таким доступом
                        access = true;
                        break;
                    }
                    findPath += ".";
                }
            }
            if (access) {
                statement = dbConnection.prepareStatement("SELECT * FROM RESOURCE where (RESOURCE.PATH like ?)");
                statement.setString(1, userInput.getPath());
                result = statement.executeQuery(); //получаем тот ресурс который запрашивали
                Resource resource = new Resource();
                while (result.next()) {
                    resource = (new Resource(Integer.valueOf(result.getString("ID"))));
                }
                log.info("Доступ к ресурсу {} разрешен", userInput.getPath());
                return resource;
            }
        } catch (SQLException e) {
            log.debug(e);
        }
        log.error("Доступ к ресурсу {} запрещен", userInput.getPath());
        return null;
    }

    public void insertRecordIntoDataBase(Connection dbConnection, Accounting journal) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("Insert into ACCOUNTING (DS, DE , VOL, RESID ) VALUES (?, ?, ?, ?)");
            statement.setDate(1, Date.valueOf(journal.getDs()));
            statement.setDate(2, Date.valueOf(journal.getDe()));
            statement.setInt(3, journal.vol);
            statement.setInt(4, journal.resId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.debug(e);
        }

    }

}
