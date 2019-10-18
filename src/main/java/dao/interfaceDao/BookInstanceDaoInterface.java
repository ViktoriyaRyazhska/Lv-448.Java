package dao.interfaceDao;

import entities.Book;
import entities.BookInstance;

import java.util.Optional;

public interface BookInstanceDaoInterface{

    void save(BookInstance bookInstance);

    Optional<BookInstance> findById(Long id);

    void update(Long id, BookInstance bookInstance);

    Book getInfoByBookInstance(Long bookInstanceId);

    boolean isAvailable(Long id);
}
