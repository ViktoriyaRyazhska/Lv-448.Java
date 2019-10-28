package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AudienceRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.IdRowMapper;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Class, that implements special methods for
 * getting / updating Audience objects from / in database.
 */
public class JdbcAudienceDao implements AudienceDao {

    /**
     * Connection used for interaction with database.
     */
    private final Connection connection;
    /**
     * this field used for implementing Singleton.
     */
    private static JdbcAudienceDao instance;

    /**
     * Constructor, that creates instance using Connection.
     *
     * @param connection Connection used for interaction with database.
     */
    private JdbcAudienceDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method for getting instance of JdbcAudienceDao.
     *
     * @param connection Connection, that used for interaction with database.
     * @return instance of JdbcAudienceDao.
     */
    public synchronized static JdbcAudienceDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new JdbcAudienceDao(connection);
        }

        return instance;
    }

    /**
     * Method for saving objects in database.
     *
     * @param objectToSave object, that must be saved.
     * @return id of last saved object.
     */
    @Override
    public long save(Audience objectToSave) {
        String SAVE_AUDIENCE = "INSERT INTO audiences (name) VALUES(?)";

        JdbcUtils.update(connection, SAVE_AUDIENCE, objectToSave.getName());

        return getLastSavedObjectId();
    }

    /**
     * Method for deleting object by id.
     *
     * @param id id of object, that must be deleted.
     */
    @Override
    public void deleteById(long id) {
        String DELETE_AUDIENCE = "DELETE FROM audiences WHERE id = ?";

        JdbcUtils.update(connection, DELETE_AUDIENCE, id);
    }

    /**
     * Method, that returns object wrapped in Optional by id.
     *
     * @param id object's id.
     * @return object wrapped in Optional that has id field value
     * the same as id parameter value.
     * If there is no object with such id value
     * it returns Optional.empty
     */
    @Override
    public Optional<Audience> findById(long id) {
        String FIND_AUDIENCE_BY_ID = "SELECT id as audience_id, name as audience_name FROM audiences WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_ID, new AudienceRowMapper(), id);
    }

    /**
     * Method, that returns all objects of T type from database.
     *
     * @return list of Audience objects from database.
     */
    @Override
    public List<Audience> findAll() {
        String FIND_ALL_AUDIENCE = "SELECT id as audience_id, name as audience_name FROM audiences";
        return JdbcUtils.query(connection, FIND_ALL_AUDIENCE, new AudienceRowMapper());
    }

    /**
     * Method, that updates given object in database.
     *
     * @param newObject object to update
     * @return changed rows count in database.
     */
    @Override
    public int update(Audience newObject) {
        String UPDATE_AUDIENCE = "UPDATE audiences set name = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_AUDIENCE, newObject.getName(), newObject.getId());
    }

    /**
     * Method for finding Audience by audience name.
     *
     * @param name audience name
     * @return Audience object wrapped in Optional or
     * Optional.empty() if there is no Audience with
     * such name.
     */
    @Override
    public Optional<Audience> findByName(String name) {
        String FIND_AUDIENCE_BY_NAME = "SELECT id as audience_id, name AS audience_name FROM audiences WHERE name = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_NAME, new AudienceRowMapper(), name);
    }

    /**
     * Method for finding Audience by employee.
     *
     * @param employee employee for which you want
     *                 to find Audience.
     * @return Audience wrapped in Optional linked with employee
     * or Optional.empty() if there is no such audience.
     */
    @Override
    public Optional<Audience> findByEmployee(Employee employee) {
        String FIND_AUDIENCE_BY_EMPLOYEE = "SELECT audiences.id as audience_id, " +
                "name as audience_name FROM audiences INNER JOIN employees " +
                "ON employees.audience_id = audiences.id and employees.id = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_EMPLOYEE, new AudienceRowMapper(), employee.getId());
    }

    /**
     * Method for finding Audience by exhibit.
     *
     * @param exhibit exhibit for which you want
     *                to find audience.
     * @return Audience wrapped in Optional linked with exhibit
     * or Optional.empty() if there is no such audience.
     */
    @Override
    public Optional<Audience> findByExhibit(Exhibit exhibit) {
        String FIND_AUDIENCE_BY_EXHIBIT_ID =
                "SELECT id AS audience_id, name AS audience_name " +
                        "FROM audiences " +
                        "WHERE id = (SELECT audience_id FROM exhibits WHERE id = ?);";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_EXHIBIT_ID, new AudienceRowMapper(), exhibit.getId());
    }

    /**
     * Method for getting last saved Audience id.
     *
     * @return id of last saved Audience.
     */
    private long getLastSavedObjectId() {
        String QUERY = "SELECT MAX(id) AS last_id FROM audiences";

        return JdbcUtils.queryForObject(connection, QUERY, new IdRowMapper()).
                orElseThrow(() -> new RuntimeException("Can't get last saved object id"));
    }
}
