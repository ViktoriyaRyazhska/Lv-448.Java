package dao.interfaceDao;

import dao.interfaceDao.Crud;
import entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDaoInterface extends Crud<User> {

    void update(Long id, User user);

    Optional<User> findById(Long id);

    List<User> findAll();

}
