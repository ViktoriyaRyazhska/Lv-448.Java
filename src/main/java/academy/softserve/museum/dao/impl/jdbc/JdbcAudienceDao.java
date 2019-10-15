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
        String SAVE_AUDIENCE = "INSERT INTO audiences (name) VALUES(?)";

        try (PreparedStatement statement = connection.prepareStatement(SAVE_AUDIENCE)) {
            statement.setString(1, objectToSave.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String DELETE_AUDIENCE = "DELETE FROM audiences WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_AUDIENCE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Audience> findById(long id) {
        String FIND_AUDIENCE_BY_ID = "SELECT id as audience_id, name as audience_name FROM audiences WHERE id = ?";
        Optional<Audience> audience = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(FIND_AUDIENCE_BY_ID)) {
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
        String FIND_ALL_AUDIENCE = "SELECT id as audience_id, name as audience_name FROM audiences";
        AudienceRowMapper rowMapper = new AudienceRowMapper();
        List<Audience> audiences = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_AUDIENCE)) {
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
        String UPDATE_AUDIENCE = "UPDATE audiences set name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AUDIENCE)) {
            statement.setString(1, newObject.getName());
            statement.setLong(2, newObject.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Audience> findByName(String name) {
        String FIND_AUDIENCE_BY_NAME = "SELECT id as audience_id, name AS audience_name WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(FIND_AUDIENCE_BY_NAME)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new AudienceRowMapper().mapRow(resultSet));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
