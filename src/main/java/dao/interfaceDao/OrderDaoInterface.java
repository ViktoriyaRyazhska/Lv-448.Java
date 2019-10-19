package dao.interfaceDao;

import entities.Order;

import java.util.List;
import java.util.Optional;


public interface OrderDaoInterface {

    void save(Order order);

    Optional<Order> findById(Long id);

    void update(Long id, Order order);

    List<Order> findAll();

    List<Order> findAllByUserId(Long userId);

}