import connection.ConnectDB;
import dao.implemetsDao.OrderDao;
import entities.Order;

import java.sql.Connection;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectDB.getConnection();
        OrderDao orderDao = new OrderDao(connection);

        Order order = Order.builder()
                .dateReturn(Date.valueOf("2015-10-10").toLocalDate()).build();
        orderDao.save(order);

    }
}
