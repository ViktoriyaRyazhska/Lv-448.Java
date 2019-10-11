package inc.softserve.dao;

import inc.softserve.entities.Usr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return 0;
    }

    @Override
    public int delete(Usr usr) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<Usr> findAll() {
        return null;
    }

    private List<Usr> extractUsers(ResultSet rs){
        try {
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
            return users;
        } catch (SQLException e) {
            //TODO - add logging
            throw new RuntimeException(e);
        }
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
        return Optional.ofNullable(usr);
    }
}
