package service;

import dao.implemetsDao.UserDao;
import utils.CalculateDateFromInt;

import java.time.LocalDate;

public class UserService {
    private UserDao userDao;

    public Integer averageAgeOfUsers() {
        return userDao.averageAgeOfUsers();
    }

    public String averageTimeOfUsingLibrary() {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.averageTimeUsingLibrary());
    }

    public String timeOfUsingLibraryByUser(Long id) {
        return CalculateDateFromInt.calculateDaysFromInt(userDao.timeUsingLibraryByUser(id));
    }

    public Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate){
        return userDao.averageAmountOfOrdersBySomePeriod(fromDate,toDate);
    }
}
