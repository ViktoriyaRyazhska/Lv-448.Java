package dao.interfaceDao;

import entities.Book;
import entities.Order;

import java.time.LocalDate;
import java.util.List;


public interface OrderDaoInterface extends Crud<Order> {
    List<Order> findAll();

    List<Order> findAllByUserId(Long userId);

}