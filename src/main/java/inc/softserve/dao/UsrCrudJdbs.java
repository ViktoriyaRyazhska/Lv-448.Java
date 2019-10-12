package inc.softserve.dao;

import inc.softserve.entities.Usr;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsrCrudJdbs implements Crud<Usr> {

    private final Connection connection;

    public UsrCrudJdbs(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(Usr usr) {
        String query = "INSERT INTO users " +
                "(id, email, phone_number, password_hash, salt, firstname, lastname, role, birth_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, usr.getId());
            prepStat.setString(2, usr.getEmail());
            prepStat.setString(3, usr.getPhoneNumber());
            prepStat.setString(4, usr.getPasswordHash());
            prepStat.setString(5, usr.getSalt());
            prepStat.setString(6, usr.getFirstName());
            prepStat.setString(7, usr.getLastName());
            prepStat.setString(8, usr.getRole().toString()); // TODO
            prepStat.setDate(9, Date.valueOf(usr.getBirthDate()));
            return prepStat.executeUpdate();
        } catch (SQLException e) {
            //TODO - add logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Usr usr) {
        String query = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setString(1, usr.getEmail());
            return prepStat.executeUpdate();
        } catch (SQLException e) {
            // TODO - add logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)){
            return prepStat.executeUpdate();
        } catch (SQLException e) {
            // TODO - add logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usr> findAll() {
        String query = "SELECT * FROM users";
        try (PreparedStatement prepStat = connection.prepareStatement(query)){
            return extractUsers(prepStat.executeQuery());
        } catch (SQLException e) {
            // TODO - add logging
            throw new RuntimeException(e);
        }
    }

    private List<Usr> extractUsers(ResultSet rs) throws SQLException {
        List<Usr> users = new ArrayList<>();
        while (rs.next()){
            Usr usr = new Usr();
            usr.setId(rs.getLong("id"));
            usr.setEmail(rs.getString("email"));
            usr.setPhoneNumber(rs.getString("phone_number"));
            usr.setFirstName(rs.getString("firstname"));
            usr.setLastName(rs.getString("lastname"));
            usr.setBirthDate(rs.getDate("birth_date").toLocalDate());
            users.add(usr);
        }
        rs.close();
        return users;
    }

    @Override
    public Optional<Usr> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractUsr(resultSet);
        } catch (SQLException e) {
            //TODO - add logging
            throw new RuntimeException(e);
        }
    }

    private Optional<Usr> extractUsr(ResultSet rs) throws SQLException {
        Usr usr = null;
        while (rs.next()){
            usr = new Usr();
            usr.setId(rs.getLong("id"));
            usr.setEmail(rs.getString("email"));
            usr.setPhoneNumber(rs.getString("phone_number"));
            usr.setFirstName(rs.getString("firstname"));
            usr.setLastName(rs.getString("lastname"));
            usr.setBirthDate(rs.getDate("birth_date").toLocalDate());
        }
        rs.close();
        return Optional.ofNullable(usr);
    }

    @Override
    public Optional<Usr> findByUniqueField(String unique) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)){
            prepStat.setString(1, unique);
            return extractUsr(prepStat.executeQuery());
        } catch (SQLException e) {
            // TODO - add logging
            throw new RuntimeException(e);
        }
    }
}
