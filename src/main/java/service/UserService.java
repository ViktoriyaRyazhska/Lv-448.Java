package service;

import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.UserDao;
import utils.CalculateDateFromInt;

import java.time.LocalDate;

public class UserService {
    private UserDao userDao;
    private BookInstanceDao bookInstanceDao;

    public UserService(UserDao userDao, BookInstanceDao bookInstanceDao) {
        this.userDao = userDao;
        this.bookInstanceDao = bookInstanceDao;
    }

    public Integer averageAgeOfUsers() {
        return userDao.averageAgeOfUsers();
    }

    public Integer[] averageTimeOfUsingLibrary() {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.averageTimeUsingLibrary());
    }

    public Integer[] timeOfUsingLibraryByUser(Long id) {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.timeUsingLibraryByUser(id));
    }

    public Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate) {
        return userDao.averageAmountOfOrdersBySomePeriod(fromDate, toDate);
    }

    public Integer averageAgeUsersByBook(Long bookId) {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.averageAgeUsersByBook(bookId))[0];
    }

    public Integer averageAgeUsersByAuthor(Long authorId) {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.averageAgeUsersByAuthor(authorId))[0];
    }
}
