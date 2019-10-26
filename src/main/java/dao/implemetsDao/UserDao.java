package dao.implemetsDao;

import dao.interfaceDao.AddressDaoInterface;
import dao.interfaceDao.BookInstanceDaoInterface;
import dao.interfaceDao.UserDaoInterface;
import entities.BookInstance;
import entities.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constants.QueryConstants.MAX_DAYS_TO_RETURN;

public class UserDao implements UserDaoInterface {

    /**
     * The connection field used for interaction with database.
     */
    private final Connection connection;

    /**
     * The field used for loading and setting address for user.
     */
    private AddressDaoInterface addressDaoInterface;

    /**
     * The field used for method getBlackList.
     */
    private BookInstanceDaoInterface bookInstanceDaoInterface;
    /**
     * The userDao field used for implementing Singleton.
     */
    private static UserDao userDao;

    /**
     * Constructor, which creates an instance of the class using connection.
     *
     * @param connection used for interaction with database.
     */
    private UserDao(Connection connection, AddressDaoInterface addressDaoInterface, BookInstanceDaoInterface bookInstanceDaoInterface) {
        this.connection = connection;
        this.addressDaoInterface = addressDaoInterface;
        this.bookInstanceDaoInterface = bookInstanceDaoInterface;
    }

    /**
     * Method for getting an instance of UserDao class.
     *
     * @param connection used for interaction with database.
     * @return an instance of UserDao class.
     */
    public static UserDao getInstance(Connection connection, AddressDaoInterface addressDao, BookInstanceDaoInterface bookInstanceDao) {
        if (userDao == null) {
            userDao = new UserDao(connection, addressDao, bookInstanceDao);
        }

        return userDao;
    }

    /**
     * Method which saves objects in database.
     *
     * @param user object which must be saved.
     */
    @Override
    public void save(User user) {
        String query = "INSERT INTO users"
                + "(user_name, user_surname, birthday, phone_number, email, date_registration, id_address) "
                + "VALUE (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserSurname());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setDate(6, Date.valueOf(LocalDate.now()));
            preparedStatement.setLong(7, user.getUserAddress().getId());
            preparedStatement.executeUpdate();
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * Method used for finding all users from the database.
     *
     * @return list of User objects from database.
     */
    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractUsers(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for finding average time of
     * using the library
     *
     * @return average number of days
     * users are using the library.
     */
    @Override
    public Integer averageTimeUsingLibrary() {
        String query = "select AVG(DATEDIFF(CURDATE(), date_registration)) from users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for finding the period of time
     * the user is using the library.
     *
     * @param userId user's id
     * @return number of days using the library
     * by user id.
     */
    @Override
    public Integer timeUsingLibraryByUser(Long userId) {
        String query = "select DATEDIFF(CURDATE(), date_registration) from users where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for finding average age of all users.
     *
     * @return average age of users.
     */
    @Override
    public Integer averageAgeOfUsers() {
        String query = "SELECT AVG(YEAR(NOW()) - YEAR(users.birthday)) as avg_age FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Статистика по читачам середня кількість звернень за певний період

    /**
     * Method used for finding an average amount of orders
     * by some period.
     *
     * @param fromDate start date
     * @param toDate  end date
     * @return number of orders by some period.
     */
    @Override
    public Integer averageAmountOfOrdersBySomePeriod(LocalDate fromDate, LocalDate toDate) {
        String query = "SELECT COUNT(*) FROM orders WHERE date_order between ? and ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Optional.ofNullable((Date.valueOf(fromDate))).orElse(null));
            preparedStatement.setDate(2, Optional.ofNullable((Date.valueOf(toDate))).orElse(null));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for updating objects in database.
     *
     * @param user object to update.
     */
    @Override
    public void update(User user) {
        String query = "UPDATE users SET user_name = ?, user_surname = ?, birthday = ?, phone_number = ?,"
                + " email = ?, id_address = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserSurname());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setLong(6, user.getUserAddress().getId());
            preparedStatement.setLong(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * Method used for finding a User by his id.
     *
     * @param id user's id.
     * @return User object wrapped in Optional.
     *
     * In case of absence an object with such id
     * method returns Optional.empty().
     */
    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractUsers(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<User> extractUsers(ResultSet resultSet) throws SQLException {
        Stream.Builder<User> builder = Stream.builder();
        while (resultSet.next()) {
            builder.add(
                    User.builder()
                            .id(resultSet.getLong("id"))
                            .userName(resultSet.getString("user_name"))
                            .userSurname(resultSet.getString("user_surname"))
                            .birthday(resultSet.getDate("birthday").toLocalDate())
                            .phoneNumber(resultSet.getString("phone_number"))
                            .email(resultSet.getString("email"))
                            .registrationDate(resultSet.getDate("date_registration").toLocalDate())
                            .userAddress(addressDaoInterface.findById(resultSet.getLong("id_address")).get())
                            .build());
        }
        resultSet.close();
        return builder.build();
    }

    /**
     * Method used for finding an average age of users
     * which reads some book
     *
     * @param bookId book's id
     * @return average users' age by book id
     */
    @Override
    public Integer averageAgeUsersByBook(Long bookId) {
        String query = "select AVG(DATEDIFF(CURDATE(), users.birthday)) from users " +
                "inner join orders on users.id = orders.id_users " +
                "inner join book_instance bi on orders.id_book_instance = bi.id " +
                "inner join books b on bi.id_book = b.id where b.id = ?;";
        return averageAgeUserForUserDao(bookId, query);
    }

    /**
     * Method used for finding all users who has not returned
     * some book on time(debtors)
     *
     * @return map of elements where the key is a book instance
     * which was not returned on time and
     * the value is a user who is a debtor
     */
    public Map<BookInstance, User> geBlackList() {
        String query = "SELECT users.id, id_book_instance FROM users inner join orders o on users.id = o.id_users where date_return is null and DATEDIFF(CURDATE(), date_order) > ?;";
        Map<BookInstance, User> userBookMap = new LinkedHashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, MAX_DAYS_TO_RETURN);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userBookMap.put(bookInstanceDaoInterface.findById(resultSet.getLong("id_book_instance")).get(),
                        findById(resultSet.getLong("id")).get());
            }
            return userBookMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for finding an average age of users
     * who reads the author
     *
     * @param authorId author's name
     * @return average age of users
     * by some author
     */
    @Override
    public Integer averageAgeUsersByAuthor(Long authorId) {
        String query = "select AVG(DATEDIFF(CURDATE(), users.birthday)) from users " +
                "inner join orders on users.id = orders.id_users " +
                "inner join book_instance bi on orders.id_book_instance = bi.id " +
                "inner join books b on bi.id_book = b.id " +
                "inner join authors on b.id_author = authors.id where authors.id = ?;";
        return averageAgeUserForUserDao(authorId, query);
    }

    private Integer averageAgeUserForUserDao(Long id, String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AVG(DATEDIFF(CURDATE(), users.birthday))");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
