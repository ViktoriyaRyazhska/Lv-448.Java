package database;

import dao.implemetsDao.*;
import dao.interfaceDao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DaoFactory {

    private static Connection connection;
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/"
            + "library?"
            + "useSSL=false&"
            + "serverTimezone=UTC&"
            + "allowPublicKeyRetrieval=true";


    /**
     * Private constructor used for Singleton implementation
     */
    private DaoFactory() {
    }

    static {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for creating AddressDao instance
     *
     * @return AddressDao instance
     */
    public static AddressDaoInterface addressDao() {
        return AddressDao.getInstance(connection);
    }

    /**
     * Method used for creating AuthorDao instance
     *
     * @return AuthorDao instance
     */
    public static AuthorDaoInterface authorDao() {
        return AuthorDao.getInstance(connection);
    }

    /**
     * Method used for creating BookDao instance
     *
     * @return BookDao instance
     */
    public static BookDao bookDao() {
        return BookDao.getInstance(connection, authorDao());
    }

    /**
     * Method used for creating BookInstance instance
     *
     * @return BookInstanceDao instance
     */
    public static BookInstanceDaoInterface bookInstanceDao() {
        BookDao bookDao = BookDao.getInstance(connection, authorDao());
        BookInstanceDao bookInstanceDao = BookInstanceDao.getInstance(connection, bookDao);

        return bookInstanceDao;
    }

    /**
     * Method used for creating UserDao instance
     *
     * @return UserDao instance
     */
    public static UserDaoInterface userDao() {
        UserDao userDao = UserDao.getInstance(connection, addressDao(), bookInstanceDao());

        return userDao;
    }

    /**
     * Method used for creating OrderDao instance
     *
     * @return OrderDao instance
     */
    public static OrderDaoInterface orderDao() {
        OrderDao orderDao = OrderDao.getInstance(connection, userDao(), bookInstanceDao());

        return orderDao;
    }

    /**
     * Method for closing connection.
     * Must be called in the end of the program
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
