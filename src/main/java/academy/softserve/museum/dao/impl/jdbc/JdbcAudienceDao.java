package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AudienceRowMapper;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JdbcAudienceDao implements AudienceDao {

    private final Connection connection;
    private static JdbcAudienceDao instance;

    private JdbcAudienceDao(Connection connection) {
        this.connection = connection;
    }

    public static JdbcAudienceDao getInstance(Connection connection){
        if(instance == null){
            instance = new JdbcAudienceDao(connection);
        }

        return instance;
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

    @Override
    public Optional<Audience> findByEmployee(Employee employee) {
        String FIND_AUDIENCE_BY_EMPLOYEE = "SELECT audiences.id as audience_id, " +
                "name as audience_name FROM audiences INNER JOIN employees " +
                "ON employees.audience_id = audiences.id and employees.id = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_EMPLOYEE, new AudienceRowMapper(), employee.getId());
    }

    @Override
    public Optional<Audience> findByExhibit(Exhibit exhibit) {
        String FIND_AUDIENCE_BY_EXHIBIT_ID =
                "SELECT id AS audience_id, name AS audience_name " +
                        "FROM audiences " +
                        "WHERE id = (SELECT audience_id FROM exhibits WHERE id = ?);";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_EXHIBIT_ID, new AudienceRowMapper(), exhibit.getId());
    }

}
