package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AudienceRowMapper;
import academy.softserve.museum.entities.Audience;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAudienceDao implements AudienceDao {

    private Connection connection;

    public JdbcAudienceDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Audience objectToSave) {
        String saveAudience = "INSERT INTO audiences (name) VALUES(?)";

        try (PreparedStatement statement = connection.prepareStatement(saveAudience)) {
            statement.setString(1, objectToSave.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String deleteAudience = "DELETE FROM audiences WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteAudience)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Audience> findById(long id) {
        String findAudienceById = "SELECT id as audience_id, name as audience_name FROM audiences WHERE id = ?";
        Optional<Audience> audience = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(findAudienceById)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    audience = Optional.of(new AudienceRowMapper().mapRow(resultSet));
                }
            }

            return audience;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Audience> findAll() {
        String findAllAudience = "SELECT id as audience_id, name as audience_name FROM audiences";
        AudienceRowMapper rowMapper = new AudienceRowMapper();
        List<Audience> audiences = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(findAllAudience)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    audiences.add(rowMapper.mapRow(resultSet));
                }
            }

            return audiences;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Audience newObject) {
        String updateAudience = "UPDATE audiences set name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateAudience)) {
            statement.setString(1, newObject.getName());
            statement.setLong(2, newObject.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO method findByName
    @Override
    public Optional<Audience> findByName(String name) {
        return Optional.empty();
    }
}
