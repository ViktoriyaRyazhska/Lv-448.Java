package academy.softserve.museum.database;

import academy.softserve.museum.dao.*;
import academy.softserve.museum.dao.impl.jdbc.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class that contains Connection and creates instances of DAO classes
 */
public final class DaoFactory {

    /**
     * Connection used for interaction with database
     * This connection will be passed to every DAO instance
     */
    private static final Connection connection;

    /**
     * Private constructor used for Singleton implementation
     */
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

    /**
     * Method for creating AuthorDao instance
     *
     * @return AuthorDao instance
     */
    public static AuthorDao authorDao() {
        JdbcAuthorDao authorDao = JdbcAuthorDao.getInstance(connection);
        JdbcExhibitDao exhibitDao = JdbcExhibitDao.getInstance(connection, audienceDao());

        authorDao.setExhibitDao(exhibitDao);
        exhibitDao.setAuthorDao(authorDao);

        return authorDao;
    }

    /**
     * Method for creating EmployeeDao instance
     *
     * @return EmployeeDao instance
     */
    public static EmployeeDao employeeDao() {
        return JdbcEmployeeDao.getInstance(connection, audienceDao());
    }

    /**
     * Method for creating ExcursionDao instance
     *
     * @return ExcursionDao instance
     */
    public static ExcursionDao excursionDao() {
        return JdbcExcursionDao.getInstance(connection);
    }

    /**
     * Method for creating ExhibitDao instance
     *
     * @return ExhibitDao instance
     */
    public static ExhibitDao exhibitDao() {
        JdbcAuthorDao authorDao = JdbcAuthorDao.getInstance(connection);
        JdbcExhibitDao exhibitDao = JdbcExhibitDao.getInstance(connection, audienceDao());

        authorDao.setExhibitDao(exhibitDao);
        exhibitDao.setAuthorDao(authorDao);

        return exhibitDao;
    }

    /**
     * Method for creating TimetableDao instance
     *
     * @return TimetableDao instance
     */
    public static TimetableDao timetableDao() {
        return JdbcTimetableDao.getInstance(connection);
    }

    /**
     * Method for creating AudienceDao instance
     *
     * @return AudienceDao instance
     */
    public static AudienceDao audienceDao() {
        return JdbcAudienceDao.getInstance(connection);
    }

    /**
     * Method for closing Connection. Should be called
     * at the end of the program.
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
