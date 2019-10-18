package dao.interfaceDao;

import entities.Book;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface BookDaoInterface{

    void save(Book book);

    Optional<Book> findById(Long id);

    void update(Long id, Book book);

    List<Book> findAllByAuthorSurname(String authorName);

    List<Book> findAllBooksBySubAuthor(Long subAuthorId);

    List<Book> findAll();

    List<Book> booksReleasedDuringIndependence(LocalDate fromDate, LocalDate toDate);

    List<Book> mostPopularBooks(LocalDate startPeriod, LocalDate endPeriod);


}
