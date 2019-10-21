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

public class JdbcExcursionDao implements ExcursionDao {

    private Connection connection;
    private static JdbcExcursionDao instance;

    private JdbcExcursionDao(Connection connection) {
        this.connection = connection;
    }

    public static JdbcExcursionDao getInstance(Connection connection) {
        if(instance == null){
            instance = new JdbcExcursionDao(connection);
        }

        return instance;
    }

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

    @Override
    public Optional<Excursion> findByName(String name) {
        String FIND_EXCURSION_BY_NAME = "SELECT id AS excursion_id, name AS excursion_name FROM excursion " +
                "WHERE name = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXCURSION_BY_NAME, new ExcursionRowMapper(), name);
    }

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

    @Override
    public long save(Excursion objectToSave) {
        String SAVE_EXCURSION = "INSERT INTO excursion(name) values(?)";

        JdbcUtils.update(connection, SAVE_EXCURSION, objectToSave.getName());

        return getLastSavedObjectId();
    }

    @Override
    public void deleteById(long id) {
        String DELETE_EXCURSION = "DELETE FROM excursion WHERE id = ?";

        JdbcUtils.update(connection, DELETE_EXCURSION, id);
    }

    @Override
    public Optional<Excursion> findById(long id) {
        String FIND_EXCURSION_BY_ID = "SELECT id AS excursion_id, name AS excursion_name FROM excursion WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXCURSION_BY_ID, new ExcursionRowMapper(), id);
    }

    @Override
    public List<Excursion> findAll() {
        String FIND_ALL_EXCURSIONS = "SELECT id AS excursion_id, name AS excursion_name FROM excursion";

        return JdbcUtils.query(connection, FIND_ALL_EXCURSIONS, new ExcursionRowMapper());
    }

    @Override
    public int update(Excursion newObject) {
        String UPDATE_EXCURSION = "UPDATE excursion SET name = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_EXCURSION, newObject.getName(), newObject.getId());
    }

    private long getLastSavedObjectId() {
        String QUERY = "SELECT MAX(id) AS last_id FROM excursion";

        return JdbcUtils.queryForObject(connection, QUERY, new IdRowMapper()).
                orElseThrow(() -> new RuntimeException("Can't get last saved object id"));
    }
}
