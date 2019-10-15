package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionStatisticRowMapper;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                "SELECT e.id, e.name, count(e.id) AS excursion_count " +
                        "FROM excursion AS e " +
                        "INNER JOIN timetable AS tt " +
                        "ON tt.excursion_id = e.id " +
                        "WHERE date_start > ? and date_end < ? " +
                        "GROUP BY e.id, e.name;";

        ExcursionStatistic statistic;
        try (PreparedStatement statement = connection.prepareStatement(FIND_STATISTIC)) {
            statement.setDate(1, dateStart);
            statement.setDate(2, dateEnd);

            try (ResultSet resultSet = statement.executeQuery()) {
                statistic = new ExcursionStatisticRowMapper().mapRow(resultSet);
                statistic.setDateStart(dateStart);
                statistic.setDateEnd(dateEnd);

                return statistic;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO Method findByName
    @Override
    public Optional<Excursion> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void save(Excursion objectToSave) {
        String SAVE_EXCURSION = "INSERT INTO excursion(name) values(?)";

        try (PreparedStatement statement = connection.prepareStatement(SAVE_EXCURSION)) {
            statement.setString(1, objectToSave.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String DELETE_EXCURSION = "DELETE FROM excursion WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_EXCURSION)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Excursion> findById(long id) {
        String FIND_EXCURSION_BY_ID = "SELECT * FROM excursion WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(FIND_EXCURSION_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new ExcursionRowMapper().mapRow(resultSet));
                }

                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Excursion> findAll() {
        String FIND_ALL_EXCURSIONS = "SELECT * FROM excursion";
        ExcursionRowMapper rowMapper = new ExcursionRowMapper();
        List<Excursion> excursions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_EXCURSIONS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    excursions.add(rowMapper.mapRow(resultSet));
                }

                return excursions;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Excursion newObject) {
        String UPDATE_EXCURSION = "UPDATE excursion SET name = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXCURSION)) {
            statement.setString(1, newObject.getName());
            statement.setLong(2, newObject.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
