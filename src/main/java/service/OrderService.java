package service;

import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.OrderDao;
import dao.implemetsDao.UserDao;
import entities.BookInstance;
import entities.Order;

public class OrderService {
    private OrderDao orderDao;
    private UserDao userDao;
    private BookInstanceDao bookInstanceDao;

    public void createOrder(){
        Order order = Order.builder().build();
        order.setBookInstance(bookInstanceDao.findById(1L).get());
        order.setUser(userDao.findById(1L).get());
        orderDao.save(order);
    }
}
