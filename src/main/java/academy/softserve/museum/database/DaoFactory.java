package academy.softserve.museum.database;

import academy.softserve.museum.dao.*;
import academy.softserve.museum.dao.impl.jdbc.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DaoFactory {
    private static final Connection connection;

    private DaoFactory() {
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("db_url"),
                    System.getenv("db_user"), System.getenv("db_password"));
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

    public static TimetableDao timetableDao() {
        return new JdbcTimetableDao(connection);
    }

    public static AudienceDao audienceDao() {
        return new JdbcAudienceDao(connection);
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
