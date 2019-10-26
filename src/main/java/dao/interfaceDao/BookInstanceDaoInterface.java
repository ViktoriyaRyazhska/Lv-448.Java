package dao.interfaceDao;

import entities.Book;
import entities.BookInstance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookInstanceDaoInterface{

    void save(BookInstance bookInstance);

    Optional<BookInstance> findById(Long id);

    void update(Long bookInstanceId, Boolean available);

    boolean isAvailable(Long id);

    Long getAmountOfTimesInstanceWasTaken(Long id);

    List<BookInstance> findAllReturnedBookInstanceByUser(Long userId);

    List<BookInstance> findAllBookInstanceOnReading(Long userId);

    List<BookInstance> findAllBookInstanceByTitle(String bookTitle);

    Map<BookInstance, Long> amountBookInstanceWasOrderedByPeriod(LocalDate firstDate, LocalDate secondDate);

    List<BookInstance> findAllBookInstanceByBookId(Long bookId);
}
