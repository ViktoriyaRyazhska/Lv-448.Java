package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionStatisticRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.IdRowMapper;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Class, that contains special methods for
 * getting / updating Excursion objects from / in database.
 */
public class JdbcExcursionDao implements ExcursionDao {

    /**
     * Connection used for interaction with database.
     */
    private Connection connection;
    /**
     * this field used for implementing Singleton.
     */
    private static JdbcExcursionDao instance;

    /**
     * Constructor, that creates instance using Connection.
     *
     * @param connection Connection used for interaction with database.
     */
    private JdbcExcursionDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method for getting instance of JdbcExcursionDao.
     *
     * @param connection Connection, that used for interaction with database.
     * @return instance of JdbcExcursionDao.
     */
    public synchronized static JdbcExcursionDao getInstance(Connection connection) {
        if(instance == null){
            instance = new JdbcExcursionDao(connection);
        }

        return instance;
    }

    /**
     * Method for finding statistic for all Excursions.
     * Statistic contains conducted excursions count
     * for each possible excursion.
     *
     * @param dateStart date and time for statistic calculating start.
     * @param dateEnd   date and time for statistic calculating end.
     * @return statistic.
     */
    @Override
    public ExcursionStatistic findStatistic(Date dateStart, Date dateEnd) {
        String FIND_STATISTIC =
                "SELECT e.id as excursion_id, e.name as excursion_name, count(e.id) AS excursion_count " +
                        "FROM excursion AS e " +
                        "INNER JOIN timetable AS tt " +
                        "ON tt.excursion_id = e.id " +
                        "WHERE date_start > ? and date_end < ? " +
                        "GROUP BY e.id, e.name;";

        ExcursionStatistic emptyStatistic = new ExcursionStatistic();
        emptyStatistic.setExcursionCountMap(new LinkedHashMap<>());

        ExcursionStatistic statistic = JdbcUtils.queryForObject(connection, FIND_STATISTIC,
                new ExcursionStatisticRowMapper(), dateStart, dateEnd).orElse(emptyStatistic);

        statistic.setDateStart(dateStart);
        statistic.setDateEnd(dateEnd);

        return statistic;
    }

    /**
     * Method for finding Excursion by name.
     *
     * @param name name of excursion.
     * @return Excursion object wrapped in Optional or
     * Optional.empty() if there is no Excursion with
     * such name.
     */
    @Override
    public Optional<Excursion> findByName(String name) {
        String FIND_EXCURSION_BY_NAME = "SELECT id AS excursion_id, name AS excursion_name FROM excursion " +
                "WHERE name = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXCURSION_BY_NAME, new ExcursionRowMapper(), name);
    }

    /**
     * Method for finding available Excursions in some period.
     * Excursion is available if it starts in given period.
     *
     * @param dateStart period start date and time.
     * @param dateEnd   period end date and time.
     * @return list of available Excursions.
     */
    @Override
    public List<Excursion> findAvailable(Date dateStart, Date dateEnd) {
        String AVAILABLE_EXCURSIONS =
                "SELECT DISTINCT e.id AS excursion_id, e.name AS excursion_name " +
                        "FROM timetable AS tt " +
                        "INNER JOIN excursion AS e " +
                        "ON e.id = tt.excursion_id  " +
                        "WHERE date_start BETWEEN ? AND ?";

        return JdbcUtils.query(connection, AVAILABLE_EXCURSIONS, new ExcursionRowMapper(), dateStart, dateEnd);
    }

    /**
     * Method for saving Excursions in database.
     *
     * @param objectToSave Excursion, that must be saved.
     * @return id of last saved Excursion.
     */
    @Override
    public long save(Excursion objectToSave) {
        String SAVE_EXCURSION = "INSERT INTO excursion(name) values(?)";

        JdbcUtils.update(connection, SAVE_EXCURSION, objectToSave.getName());

        return getLastSavedObjectId();
    }

    /**
     * Method for deleting Excursions by id.
     *
     * @param id id of Excursion, that must be deleted.
     */
    @Override
    public void deleteById(long id) {
        String DELETE_EXCURSION = "DELETE FROM excursion WHERE id = ?";

        JdbcUtils.update(connection, DELETE_EXCURSION, id);
    }

    /**
     * Method, that returns Excursion wrapped in Optional by id.
     *
     * @param id Excursion's id.
     * @return Excursion wrapped in Optional that has id field value
     * the same as id parameter value.
     * If there is no Excursion with such id value
     * it returns Optional.empty
     */
    @Override
    public Optional<Excursion> findById(long id) {
        String FIND_EXCURSION_BY_ID = "SELECT id AS excursion_id, name AS excursion_name FROM excursion WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXCURSION_BY_ID, new ExcursionRowMapper(), id);
    }

    /**
     * Method, that returns all Excursions from database.
     *
     * @return list of Excursions from database.
     */
    @Override
    public List<Excursion> findAll() {
        String FIND_ALL_EXCURSIONS = "SELECT id AS excursion_id, name AS excursion_name FROM excursion";

        return JdbcUtils.query(connection, FIND_ALL_EXCURSIONS, new ExcursionRowMapper());
    }

    /**
     * Method, that updates given Excursion in database.
     *
     * @param newObject Excursion to update
     * @return changed rows count in database.
     */
    @Override
    public int update(Excursion newObject) {
        String UPDATE_EXCURSION = "UPDATE excursion SET name = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_EXCURSION, newObject.getName(), newObject.getId());
    }

    /**
     * Method for getting last saved Excursion id.
     *
     * @return id of last saved Excursion.
     */
    private long getLastSavedObjectId() {
        String QUERY = "SELECT MAX(id) AS last_id FROM excursion";

        return JdbcUtils.queryForObject(connection, QUERY, new IdRowMapper()).
                orElseThrow(() -> new RuntimeException("Can't get last saved object id"));
    }
}
