package dao.interfaceDao;

import entities.Order;

import java.util.List;


public interface OrderDaoInterface extends Crud<Order> {
    List<Order> findAll();

}
