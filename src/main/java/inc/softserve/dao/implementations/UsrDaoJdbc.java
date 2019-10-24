package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.entities.Usr;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Slf4j
public class UsrDaoJdbc implements UsrDao {

    private final Connection connection;

    public UsrDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param usr - object to save
     * @return - usr object with set id if save operation was successful. The same object otherwise.
     */
    @Override
    public Usr save(Usr usr){
        String query = "INSERT INTO users (email, phone_number, password_hash, salt, firstname, lastname, role, birth_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepStat = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, usr.getEmail());
            prepStat.setString(2, usr.getPhoneNumber());
            prepStat.setString(3, usr.getPasswordHash());
            prepStat.setString(4, usr.getSalt());
            prepStat.setString(5, usr.getFirstName());
            prepStat.setString(6, usr.getLastName());
            prepStat.setString(7, usr.getRole().toString());
            if (usr.getBirthDate() != null) {
                prepStat.setDate(8, Date.valueOf(usr.getBirthDate()));
            } else {
                prepStat.setDate(8, null);
            }
            prepStat.executeUpdate();
            try (ResultSet keys = prepStat.getGeneratedKeys()){
                if (keys.next()){
                    usr.setId(keys.getLong(1));
                }
            }
            return usr;
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Lazy implementation
     * @return - all users
     */
    @Override
    public Set<Usr> findAll(){
        String query = "SELECT * FROM users";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            ResultSet resultSet = prepStat.executeQuery();
            return extractUsers(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * lazy implementation
     * @param usrId - usr's email
     * @return - usr object if there is a user in the database with given id
     */
    @Override
    public Optional<Usr> findById(Long usrId){
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, usrId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractUsers(resultSet).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * lazy implementation
     * @param email - usr's email
     * @return - usr object if there is a user in the database with given email
     */
    @Override
    public Optional<Usr> findByEmail(String email){
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setString(1, email);
            ResultSet resultSet = prepStat.executeQuery();
            return extractUsers(resultSet).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Stream<Usr> extractUsers(ResultSet rs) throws SQLException {
        Stream.Builder<Usr> builder = Stream.builder();
        while (rs.next()){
            Usr usr = new Usr();
            usr.setId(rs.getLong("id"));
            usr.setEmail(rs.getString("email"));
            usr.setPhoneNumber(rs.getString("phone_number"));
            usr.setPasswordHash(rs.getString("password_hash"));
            usr.setSalt(rs.getString("salt"));
            usr.setFirstName(rs.getString("firstname"));
            usr.setLastName(rs.getString("lastname"));
            usr.setRole(Usr.Role.valueOf(rs.getString("role")));
            Optional<Date> date = Optional.ofNullable(rs.getDate("birth_date"));
            usr.setBirthDate(date.map(Date::toLocalDate).orElse(null));
            builder.accept(usr);
        }
        return builder.build();
    }
}
