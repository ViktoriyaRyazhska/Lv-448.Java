package dao.interfaceDao;

import entities.User;

import java.time.LocalDate;
import java.util.List;


public interface UserDaoInterface extends Crud<User> {

    void update(Long id, User user);

    List<User> findAll();

    Integer averageTimeUsingLibrary();

    Integer averageAgeOfUsers();

    Integer timeUsingLibraryByUser(Long userId);

    Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate);
}
