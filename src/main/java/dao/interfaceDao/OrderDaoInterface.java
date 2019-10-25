package dao.interfaceDao;

import entities.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface OrderDaoInterface {

    void save(Order order);

    Optional<Order> findById(Long id);

    void updateReturnDate(Long id, LocalDate dateReturn);

    List<Order> findAll();

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByUserPhoneNumber(String userPhoneNumber);

}