package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AudienceRowMapper;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.utils.JdbcUtils;

import java.sql.Connection;
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

        JdbcUtils.update(connection, SAVE_AUDIENCE, objectToSave.getName());
    }

    @Override
    public void deleteById(long id) {
        String DELETE_AUDIENCE = "DELETE FROM audiences WHERE id = ?";

        JdbcUtils.update(connection, DELETE_AUDIENCE, id);
    }

    @Override
    public Optional<Audience> findById(long id) {
        String FIND_AUDIENCE_BY_ID = "SELECT id as audience_id, name as audience_name FROM audiences WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_ID, new AudienceRowMapper(), id);
    }

    @Override
    public List<Audience> findAll() {
        String FIND_ALL_AUDIENCE = "SELECT id as audience_id, name as audience_name FROM audiences";

        return JdbcUtils.query(connection, FIND_ALL_AUDIENCE, new AudienceRowMapper());
    }

    @Override
    public void update(Audience newObject) {
        String UPDATE_AUDIENCE = "UPDATE audiences set name = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_AUDIENCE, newObject.getName(), newObject.getId());
    }

    @Override
    public Optional<Audience> findByName(String name) {
        String FIND_AUDIENCE_BY_NAME = "SELECT id as audience_id, name AS audience_name FROM audiences WHERE name = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_NAME, new AudienceRowMapper(), name);
    }

}
