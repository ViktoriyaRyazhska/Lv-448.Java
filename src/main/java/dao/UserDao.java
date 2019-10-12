package dao;

import entities.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {


    @Override
    public void save(User user) {

    }

    @Override
    public void update(Long id, User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
