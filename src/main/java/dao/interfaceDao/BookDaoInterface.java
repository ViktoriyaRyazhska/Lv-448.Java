package dao.interfaceDao;

import entities.Book;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;


public interface BookDaoInterface extends Crud<Book> {
    void update(Long id, Book book);

    List<Book> findAll();

    List<Book> findAllByAuthorName(Long authorId);

    List<Book> booksReleasedDuringIndependence(LocalDate fromDate, LocalDate toDate);

    List<Book> mostPopularBooks(LocalDate startPeriod, LocalDate endPeriod);


}
