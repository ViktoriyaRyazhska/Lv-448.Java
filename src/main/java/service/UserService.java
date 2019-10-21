package service;

import dao.implemetsDao.BookDao;
import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.UserDao;
import entities.BookInstance;
import entities.User;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.Map;

public class UserService {
    private UserDao userDao;
    private BookInstanceDao bookInstanceDao;
    private BookDao bookDao;


    public Integer averageAgeOfUsers() {
        return userDao.averageAgeOfUsers();
    }

    public String averageTimeOfUsingLibrary() {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.averageTimeUsingLibrary());
    }

    public String timeOfUsingLibraryByUser(Long id) {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.timeUsingLibraryByUser(id));
    }

    public Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate) {
        return userDao.averageAmountOfOrdersBySomePeriod(fromDate, toDate);
    }

    public Map<BookInstance, User> getBlackList() {
        Map<BookInstance, User> blackList = userDao.geBlackList();
        return blackList;
    }


}
