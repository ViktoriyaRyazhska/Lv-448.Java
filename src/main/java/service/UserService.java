package service;

import connection.ConnectDB;
import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.UserDao;
import entities.Book;
import entities.User;
import utils.CalculateDateFromInt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.QueryConstants.MAX_DAYS_TO_RETURN;

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

    public Map<User, Book> geBlackList(Connection connection) {
        String query = "SELECT * FROM users inner join orders o on users.id = o.id_users where date_return is null and DATEDIFF(CURDATE(), date_order) > 30";
        Map<User, Book> userBookMap = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userBookMap.put(userDao.findByIdUserTest(resultSet.getLong("id")),
                        bookInstanceDao.getInfoByBookInstance(resultSet.getLong("id_book_instance")));
            }
            return userBookMap;
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

}
