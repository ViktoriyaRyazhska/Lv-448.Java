package dao.implemetsDao;

import dao.interfaceDao.UserDaoInterface;
import entities.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UserDao implements UserDaoInterface {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO users"
                + "(id, user_name, user_surname, birthday, phone_number, email, date_registration) "
                + "VALUE (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserSurname());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setDate(7, Date.valueOf(user.getRegistrationDate()));
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, User user) {

    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractUsers(resultSet).findAny();
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractUsers(preparedStatement.getResultSet()).collect(Collectors.toList());
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> findAllBookInstanceOnReading(Long userId) {
        String query = "select user_name, user_surname, id_book_instance from users left join orders"
                + "on users.id = orders.id_users where date_return is null and id_users = ?";
        return findAllBookInstanceByUser(userId, query);
    }

    @Override
    public List<Long> findAllReturnedBookInstanceByUser(Long userId) {
        String query = "select user_name, user_surname, id_book_instance from users left join orders"
                + "on users.id = orders.id_users where date_return is not null and id_users = ?";
        return findAllBookInstanceByUser(userId, query);
    }

    private List<Long> findAllBookInstanceByUser(Long userId, String query) {
        List<Long> bookInstanceId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            bookInstanceId.add(preparedStatement.executeQuery().getLong("id_book_instance"));
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

        return bookInstanceId;
    }

    private Stream<User> extractUsers(ResultSet resultSet) throws SQLException {
        Stream.Builder<User> builder = Stream.builder();
        while (resultSet.next()) {
            builder.add(
                    User.builder()
                            .id(resultSet.getLong("id"))
                            .userName(resultSet.getString("user_name"))
                            .userSurname(resultSet.getString("user_surname"))
                            .birthday(Optional.ofNullable(resultSet.getDate("birthday").toLocalDate()).orElse(null))
                            .phoneNumber(resultSet.getString("phone_number"))
                            .email(resultSet.getString("email"))
                            .registrationDate(Optional.ofNullable(resultSet.getDate("date_registration").toLocalDate()).orElse(null))
                            .build());
        }
        resultSet.close();
        return builder.build();
    }


}
