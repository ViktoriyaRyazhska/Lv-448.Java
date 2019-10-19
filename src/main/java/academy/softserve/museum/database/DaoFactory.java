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
        JdbcAuthorDao authorDao = JdbcAuthorDao.getInstance(connection);
        JdbcExhibitDao exhibitDao = JdbcExhibitDao.getInstance(connection, audienceDao());

        authorDao.setExhibitDao(exhibitDao);
        exhibitDao.setAuthorDao(authorDao);

        return authorDao;
    }

    public static EmployeeDao employeeDao() {
        return JdbcEmployeeDao.getInstance(connection, audienceDao());
    }

    public static ExcursionDao excursionDao() {
        return JdbcExcursionDao.getInstance(connection);
    }

    public static ExhibitDao exhibitDao() {
        JdbcAuthorDao authorDao = JdbcAuthorDao.getInstance(connection);
        JdbcExhibitDao exhibitDao = JdbcExhibitDao.getInstance(connection, audienceDao());

        authorDao.setExhibitDao(exhibitDao);
        exhibitDao.setAuthorDao(authorDao);

        return exhibitDao;
    }

    public static TimetableDao timetableDao() {
        return JdbcTimetableDao.getInstance(connection);
    }

    public static AudienceDao audienceDao() {
        return JdbcAudienceDao.getInstance(connection);
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
