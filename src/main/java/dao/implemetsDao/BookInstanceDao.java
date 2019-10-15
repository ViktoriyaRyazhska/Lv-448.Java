package dao.implemetsDao;

import dao.interfaceDao.BookInstanceDaoInterface;
import entities.BookInstance;

import java.sql.Connection;
import java.util.Optional;

public class BookInstanceDao implements BookInstanceDaoInterface {

    private final Connection connection;

    public BookInstanceDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(BookInstance bookInstance) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<BookInstance> findById(Long id) {
        return Optional.empty();
    }
}
