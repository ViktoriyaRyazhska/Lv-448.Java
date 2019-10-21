package dao.interfaceDao;

import entities.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface BookDaoInterface {

    void save(Book book);

    Optional<Book> findById(Long id);

    void update(Book book);

    Book getInfoByBookInstance(Long bookInstanceId);

    List<Book> findAllByAuthorSurname(String authorName);

    List<Book> findAllBooksBySubAuthorId(Long subAuthorId);

    List<Book> findAll();

    List<Book> findBookBetweenDate(LocalDate fromDate, LocalDate toDate);

    Map<Book, Long> mostPopularBooks(LocalDate startPeriod, LocalDate endPeriod);

    Map<Book, Long> mostUnPopularBooks(LocalDate startPeriod, LocalDate endPeriod);


}
