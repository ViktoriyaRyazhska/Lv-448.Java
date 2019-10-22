package database;

import dao.implemetsDao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DaoFactory {

    private static Connection connection;

    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/library";

    public DaoFactory() {
    }

    static {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static AddressDao addressDao() {
        return AddressDao.getInstance(connection);
    }

    public static AuthorDao authorDao() {
        return AuthorDao.getInstance(connection);
    }

    public static BookDao bookDao() {
        AuthorDao authorDao = AuthorDao.getInstance(connection);
        BookDao bookDao = BookDao.getInstance(connection, authorDao);

        bookDao.setAuthorDao(authorDao);

        return bookDao;
    }

    public static BookInstanceDao bookInstanceDao() {
        BookDao bookDao = BookDao.getInstance(connection, authorDao());
        BookInstanceDao bookInstanceDao = BookInstanceDao.getInstance(connection, bookDao);

        return bookInstanceDao;
    }

    public static UserDao userDao() {
        UserDao userDao = UserDao.getInstance(connection, addressDao(), bookInstanceDao());

        return userDao;
    }

    public static OrderDao orderDao() {
        OrderDao orderDao = OrderDao.getInstance(connection, userDao(), bookInstanceDao());

        return orderDao;
    }

}
