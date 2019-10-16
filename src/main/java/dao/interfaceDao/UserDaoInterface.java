package dao.interfaceDao;

import entities.User;

import java.util.List;


public interface UserDaoInterface extends Crud<User> {

    void update(Long id, User user);

    List<User> findAll();

    List<Long> findAllBookInstanceOnReading(Long userId);

    List<Long> findAllReturnedBookInstanceByUser(Long userId);


}
