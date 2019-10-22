package dao.implemetsDao;

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

    private static Connection connection;
    private static UserDao userDao;
    private AddressDao addressDao;
    private BookInstanceDao bookInstanceDao;

    public UserDao(Connection connection, AddressDao addressDao, BookInstanceDao bookInstanceDao) {
        this.connection = connection;
        this.addressDao = addressDao;
        this.bookInstanceDao = bookInstanceDao;
    }

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
                            .userAddress(addressDao.findById(resultSet.getLong("id_address")).get())
                            .build());
        }
        resultSet.close();
        return builder.build();
    }

    public Integer averageAgeUsersByBook(Long bookId) {
        String query = "select AVG(DATEDIFF(CURDATE(), users.birthday)) from users " +
                "inner join orders on users.id = orders.id_users " +
                "inner join book_instance bi on orders.id_book_instance = bi.id " +
                "inner join books b on bi.id_book = b.id where b.id = ?;";
        return averageAgeUserForUserDao(bookId, query);
    }

    public Map<BookInstance, User> geBlackList() {
        String query = "SELECT users.id, id_book_instance FROM users inner join orders o on users.id = o.id_users where date_return is null and DATEDIFF(CURDATE(), date_order) > ?;";
        Map<BookInstance, User> userBookMap = new LinkedHashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, MAX_DAYS_TO_RETURN);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userBookMap.put(bookInstanceDao.findById(resultSet.getLong("id_book_instance")).get(),
                        findById(resultSet.getLong("id")).get());
            }
            return userBookMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
