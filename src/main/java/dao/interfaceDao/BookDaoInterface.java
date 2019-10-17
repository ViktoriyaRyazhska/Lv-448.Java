package dao.interfaceDao;

import entities.Book;

import java.util.List;


public interface BookDaoInterface extends Crud<Book> {

    List<Book> findAllByAuthorName(Long authorId);

    List<Book> findAllBooksBySubAuthor(Long subAuthorId);

    List<Book> findAll();

    List<Long> findAllBookInstanceIdByBookId(Long bookId);


}
