package service;

import dao.interfaceDao.BookInstanceDaoInterface;
import dao.interfaceDao.OrderDaoInterface;
import dao.interfaceDao.UserDaoInterface;
import database.DaoFactory;
import dto.OrderDto;
import entities.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private OrderDaoInterface orderDao;
    private UserDaoInterface userDao;
    private BookInstanceDaoInterface bookInstanceDao;

    /**
     * Default constructor
     */
    public OrderService() {
        this.orderDao = DaoFactory.orderDao();
        this.userDao = DaoFactory.userDao();
        this.bookInstanceDao = DaoFactory.bookInstanceDao();
    }

    /**
     * Method for creating an order in database
     *
     * @param orderToSave that must be created
     */
    public void createOrder(Order orderToSave) {
        orderDao.save(orderToSave);
    }

    /**
     * Method returns all orders from the database.
     *
     * @return list of Orders objects from database.
     */
    public List<OrderDto> findAllOrders() {
        return orderDao.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    /**
     * Method returns an order by its id.
     *
     * @param id order's id.
     * @return order object
     */
    public Order findById(Long id) {
        return orderDao.findById(id).get();
    }

    /**
     * Method updates
     * return date
     *
     * @param orderID order's id in which need to update return date
     * returns true if order was updated
     * or false if there is no such order
     */
    public boolean closeOrder(Long orderID) {
        if (orderDao.findById(orderID).get().getDateReturn() == null) {
            orderDao.updateReturnDate(orderID, LocalDate.now());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method returns all orders of user
     *
     * @param id user's id
     * @return list of orders by a user
     */
    public List<Order> findAllByUserId(Long id) {
        return orderDao.findAllByUserId(id);
    }

    /**
     * Method returns all orders by
     * user's phone number
     *
     * @param phoneNumber user's phone number
     * @return list of orders by user phone number
     */
    public List<Order> findAllByUserPhoneNumber(String phoneNumber) {
        return orderDao.findAllByUserPhoneNumber(phoneNumber);
    }

    private OrderDto convertEntityToDto(Order order) {
        OrderDto user = OrderDto.builder()
                .id(order.getId())
                .dateOrder(order.getDateOrder())
                .dateReturn(order.getDateReturn())
                .bookInstance(order.getBookInstance())
                .user(order.getUser())
                .build();
        return user;
    }

}
