package service;

import dao.interfaceDao.AddressDaoInterface;
import dao.interfaceDao.BookInstanceDaoInterface;
import dao.interfaceDao.UserDaoInterface;
import database.DaoFactory;
import entities.Address;
import entities.BookInstance;
import entities.User;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UserService {
    private UserDaoInterface userDao;
    private AddressDaoInterface addressDao;
    private BookInstanceDaoInterface bookInstanceDao;

    /**
     * Default constructor
     */
    public UserService() {
        this.userDao = DaoFactory.userDao();
        this.addressDao = DaoFactory.addressDao();
        this.bookInstanceDao = DaoFactory.bookInstanceDao();
    }

    /**
     * Method for finding a user by his id
     *
     * @param id user's id
     * @return User object
     */
    public User findUserById(Long id) {
        return userDao.findById(id).get();
    }

    /**
     * Method for updating address in database
     *
     * @param user address for updating
     * @return true if user was updated
     * or false if he doesn't exist in database
     */
    public boolean updateUser(User user) {
        if (userDao.findById(user.getId()).isPresent()) {
            userDao.update(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for creating a user in database
     *
     * @param user that must be created
     * @param address must be created before creating user
     * and then set it to the user that must be created
     */
    public void createUser(User user, Address address) {
        addressDao.save(address);
        user.setUserAddress(address);
        userDao.save(user);
    }

    /**
     * Method used for finding all users
     *
     * @return list of all users
     */
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * Method used for calculating average
     * age of present users in database
     *
     * @return number which is an average
     * age of users
     */
    public Integer averageAgeOfUsers() {
        return userDao.averageAgeOfUsers();
    }

    /**
     * Method used for calculating average time of
     * using library by users
     *
     * @return period of using the library
     * by users(years, months, days)
     */
    public String averageTimeOfUsingLibrary() {
        return printingDate(CalculateDateFromInt.calculatePeriodFromDays(userDao.averageTimeUsingLibrary()));
    }

    /**
     * Method returns time of using the library
     * by a user
     *
     * @param id user's id
     * @return period of using the library
     * by user(years, months, days)
     */
    public String timeOfUsingLibraryByUser(Long id) {
        return  printingDate(CalculateDateFromInt.calculatePeriodFromDays(userDao.averageTimeUsingLibrary()));
    }

    private String printingDate(Integer[] integers){
        return "Years: " + integers[0] + " Months: " + integers[1] + " Days: " + integers[2];
    }

    /**
     * Method returns average amount of orders
     * by some period.
     *
     * @param fromDate start date
     * @param toDate  end date
     * @return number of orders by some period.
     */
    public Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate) {
        return userDao.averageAmountOfOrdersBySomePeriod(fromDate, toDate);
    }

    /**
     * Method returns average of users who read the book
     *
     * @param bookId book's id
     * @return average age of users by a book
     */
    public Integer averageAgeUsersByBook(Long bookId) {
        return CalculateDateFromInt.calculatePeriodFromDays(userDao.averageAgeUsersByBook(bookId))[0];
    }

    /**
     * Method used for сalculating an average age of users
     * who reads the author
     *
     * @param authorId author's name
     * @return average age of users
     * by some author
     */
    public Integer averageAgeUsersByAuthor(Long authorId) {
        return CalculateDateFromInt.calculatePeriodFromDays(userDao.averageAgeUsersByAuthor(authorId))[0];
    }

    /**
     * Method used for finding all users who has not returned
     * some book on time(debtors)
     *
     * @return map of elements where the key is a book instance
     * which was not returned on time and
     * the value is a user who is a debtor
     */
    public Map<BookInstance, User> getBlackList() {
        Map<BookInstance, User> blackList = userDao.geBlackList();
        return blackList;
    }

}
