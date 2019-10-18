package dao.interfaceDao;

import entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface UserDaoInterface{

    void save(User user);

    Optional<User> findById(Long id);

    void update(Long id, User user);

    List<Long> findAllBookInstanceOnReading(Long userId);

    List<Long> findAllReturnedBookInstanceByUser(Long userId);

    List<User> findAll();

    Integer averageTimeUsingLibrary();

    Integer averageAgeOfUsers();

    Integer timeUsingLibraryByUser(Long userId);

    Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate);
}
