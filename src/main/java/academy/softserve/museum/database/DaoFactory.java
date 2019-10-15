package academy.softserve.museum.database;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcAuthorDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcEmployeeDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcExcursionDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcExhibitDao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DaoFactory {
    private static final Connection connection;
    private static final String PROPERTIES_PATH = "/home/nomadness/dev/projects/Lv-448.Java/src/main/resources/database.properties";

    private DaoFactory(){}

    static {
        Properties properties = new Properties();

        try (InputStream out = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(out);
            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("user"), properties.getProperty("password"));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static AuthorDao authorDao() {
        return new JdbcAuthorDao(connection);
    }

    public static EmployeeDao employeeDao() {
        return new JdbcEmployeeDao(connection);
    }

    public static ExcursionDao excursionDao() {
        return new JdbcExcursionDao(connection);
    }

    public static ExhibitDao exhibitDao() {
        return new JdbcExhibitDao(connection);
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
