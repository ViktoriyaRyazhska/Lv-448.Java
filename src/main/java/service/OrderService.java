package service;

import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.OrderDao;
import dao.implemetsDao.UserDao;
import entities.Order;

import java.util.List;

public class OrderService {
    private OrderDao orderDao;
    private UserDao userDao;
    private BookInstanceDao bookInstanceDao;

    public OrderService(OrderDao orderDao, UserDao userDao, BookInstanceDao bookInstanceDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.bookInstanceDao = bookInstanceDao;
    }

    public void createOrder(Order orderToSave) {
        orderDao.save(orderToSave);
    }

    public List<Order> findAllOrders() {
        return orderDao.findAll();
    }

    public Order findById(Long id) {
        return orderDao.findById(id).get();
    }

    public boolean updateReturnDate(Order orderToUpdate) {
        if (orderDao.findById(orderToUpdate.getId()).isPresent()) {
            orderDao.updateReturnDate(orderToUpdate.getId(), orderToUpdate.getDateReturn());
            return true;
        } else {
            return false;
        }
    }

    public List<Order> findAllByUserId(Long id) {
        return orderDao.findAllByUserId(id);
    }

}
