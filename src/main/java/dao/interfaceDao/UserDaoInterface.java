package dao.interfaceDao;

import entities.BookInstance;
import entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserDaoInterface{

    void save(User user);

    Optional<User> findById(Long id);

    void update(User user);

    List<User> findAll();

    Integer averageTimeUsingLibrary();

    Integer averageAgeOfUsers();

    Integer timeUsingLibraryByUser(Long userId);

    Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate);

    Map<BookInstance, User> geBlackList();

    Integer averageAgeUsersByAuthor(Long authorId);

    Integer averageAgeUsersByBook(Long bookId);
}
