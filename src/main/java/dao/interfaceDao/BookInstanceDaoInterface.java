package dao.interfaceDao;

import entities.Book;
import entities.BookInstance;

public interface BookInstanceDaoInterface extends Crud<BookInstance> {

    Book getInfoByBookInstance(Long bookInstanceId);

    boolean isAvailable(Long id);
}
