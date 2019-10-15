package dao.interfaceDao;

import entities.Author;
import entities.Book;

import java.util.List;


public interface BookDaoInterface extends Crud<Book> {
    void update(Long id, Book book);

    List<Book> findAll();

    List<Book> findAllByAuthorName(Author author);

}
