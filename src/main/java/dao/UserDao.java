package dao;

import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Crud<User> {

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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractUsers(preparedStatement.getResultSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private List<User> extractUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserSurname(resultSet.getString("userSurname"));
            user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setEmail(resultSet.getString("email"));
            user.setRegistrationDate(resultSet.getDate("date_registration").toLocalDate());
            users.add(user);
        }
        resultSet.close();
        return users;
    }


}
