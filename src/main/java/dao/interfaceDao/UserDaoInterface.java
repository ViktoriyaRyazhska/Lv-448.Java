package dao.interfaceDao;

import entities.User;

import java.util.List;


public interface UserDaoInterface extends Crud<User> {

    List<Long> findAllBookInstanceOnReading(Long userId);

    List<Long> findAllReturnedBookInstanceByUser(Long userId);

    List<User> findAll();



}
