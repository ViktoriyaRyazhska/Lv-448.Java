package service;

import dto.OrderDto;
import dao.interfaceDao.BookInstanceDaoInterface;
import dao.interfaceDao.OrderDaoInterface;
import dao.interfaceDao.UserDaoInterface;
import database.DaoFactory;
import entities.Order;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<OrderDto> findAllOrders() {
        return orderDao.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
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
