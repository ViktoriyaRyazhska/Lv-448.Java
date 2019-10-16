package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionStatisticRowMapper;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class JdbcExcursionDao implements ExcursionDao {

    private Connection connection;

    public JdbcExcursionDao(Connection connection) {
        this.connection = connection;
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
        ExcursionStatistic statistic = JdbcUtils.queryForObject(connection, FIND_STATISTIC,
                new ExcursionStatisticRowMapper(), dateStart, dateEnd).orElse(new ExcursionStatistic());

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
    public void save(Excursion objectToSave) {
        String SAVE_EXCURSION = "INSERT INTO excursion(name) values(?)";

        JdbcUtils.update(connection, SAVE_EXCURSION, objectToSave.getName());
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
    public void update(Excursion newObject) {
        String UPDATE_EXCURSION = "UPDATE excursion SET name = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_EXCURSION, newObject.getName(), newObject.getId());
    }

}
