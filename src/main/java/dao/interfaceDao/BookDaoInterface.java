package dao.interfaceDao;

import entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookDaoInterface extends Crud<Book> {
    void update(Long id, Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
