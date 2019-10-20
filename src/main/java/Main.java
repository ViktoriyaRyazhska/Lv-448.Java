import connection.ConnectDB;
import dao.implemetsDao.*;
import entities.Address;
import entities.Order;
import entities.User;
import service.OrderService;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectDB.getConnection();
//        AuthorDao authorDao = new AuthorDao(connection);
//        AddressDao addressDao = new AddressDao(connection);
//        BookDao bookDao = new BookDao(connection, authorDao);
//        UserDao userDao = new UserDao(connection);
//        BookInstanceDao bookInstanceDao = new BookInstanceDao(connection, bookDao);
//        OrderDao orderDao = new OrderDao(connection, userDao, bookInstanceDao);
//        OrderService orderService = new OrderService(orderDao, userDao, bookInstanceDao);
//        List<Order> allOrders = orderService.findAllOrders();
//        allOrders.stream().forEach(System.out::println);


    }
}
