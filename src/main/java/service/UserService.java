package service;

import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.UserDao;
import entities.Book;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserDao userDao;
    private BookInstanceDao bookInstanceDao;

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

    public List<Book> findAllBookByUserReading(Long userId) {
        List<Long> allBookInstanceOnReading = userDao.findAllBookInstanceOnReading(userId);
        List<Book> books = new ArrayList<>();
        while (allBookInstanceOnReading.iterator().hasNext()) {
            books.add(bookInstanceDao.getInfoByBookInstance(allBookInstanceOnReading.iterator().next()));
        }
        return books;
    }

    public List<Book> findAllReturnedBookByUser(Long userId) {
        List<Long> allReturnedBookInstanceByUser = userDao.findAllReturnedBookInstanceByUser(userId);
        List<Book> books = new ArrayList<>();
        while (allReturnedBookInstanceByUser.iterator().hasNext()) {
            books.add(bookInstanceDao.getInfoByBookInstance(allReturnedBookInstanceByUser.iterator().next()));
        }
        return books;
    }
}
