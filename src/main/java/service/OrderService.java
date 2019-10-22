package service;

import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.OrderDao;
import dao.implemetsDao.UserDao;
import dao.interfaceDao.BookInstanceDaoInterface;
import dao.interfaceDao.OrderDaoInterface;
import dao.interfaceDao.UserDaoInterface;
import database.DaoFactory;
import entities.Order;

import java.util.List;

public class OrderService {
    private OrderDaoInterface orderDao;
    private UserDaoInterface userDao;
    private BookInstanceDaoInterface bookInstanceDao;

    public OrderService() {
        this.orderDao = DaoFactory.orderDao();
        this.userDao = DaoFactory.userDao();
        this.bookInstanceDao = DaoFactory.bookInstanceDao();
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
