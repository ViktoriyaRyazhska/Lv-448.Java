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
            + "useUnicode=true&"
            + "useSSL=false&"
            + "serverTimezone=UTC&"
            + "allowPublicKeyRetrieval=true";

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

    public static AddressDaoInterface addressDao() {
        return AddressDao.getInstance(connection);
    }

    public static AuthorDaoInterface authorDao() {
        return AuthorDao.getInstance(connection);
    }

    public static BookDao bookDao() {
        return BookDao.getInstance(connection, authorDao());
    }

    public static BookInstanceDaoInterface bookInstanceDao() {
        BookDao bookDao = BookDao.getInstance(connection, authorDao());
        BookInstanceDao bookInstanceDao = BookInstanceDao.getInstance(connection, bookDao);

        return bookInstanceDao;
    }

    public static UserDaoInterface userDao() {
        UserDao userDao = UserDao.getInstance(connection, addressDao(), bookInstanceDao());

        return userDao;
    }

    public static OrderDaoInterface orderDao() {
        OrderDao orderDao = OrderDao.getInstance(connection, userDao(), bookInstanceDao());

        return orderDao;
    }

}
