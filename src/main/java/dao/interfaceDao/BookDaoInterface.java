package dao.interfaceDao;

import entities.Book;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;


public interface BookDaoInterface extends Crud<Book> {

    List<Book> findAllByAuthorName(Long authorId);

    List<Book> findAllBooksBySubAuthor(Long subAuthorId);

    List<Book> findAll();

    List<Long> findAllBookInstanceIdByBookId(Long bookId);


    List<Book> booksReleasedDuringIndependence(LocalDate fromDate, LocalDate toDate);

    List<Book> mostPopularBooks(LocalDate startPeriod, LocalDate endPeriod);


}
