package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.TimetableDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeRowMaper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.TimetableRowMapper;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;
import com.sun.org.apache.bcel.internal.generic.Select;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTimetableDao implements TimetableDao {
    private final Connection connection;

    public JdbcTimetableDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Employee> findAvailableTourGuides(Date dateStart, Date dateEnd) {
        String FIND_AVAILABLE_TOUR_GUIDES = "SELECT DISTINCT e.id, e.first_name, e.last_name, e.position, e.login, e.password " +
                "FROM employees AS e " +
                "WHERE e.id NOT IN(" +
                "SELECT employee_id FROM timetable " +
                "WHERE date_start BETWEEN ? AND ? " +
                "OR date_end BETWEEN ? AND ?)";

        List<Employee> employees = new ArrayList<>();
        EmployeeRowMaper rowMaper = new EmployeeRowMaper();

        try (PreparedStatement statement = connection.prepareStatement(FIND_AVAILABLE_TOUR_GUIDES)) {
            statement.setDate(1, dateStart);
            statement.setDate(2, dateEnd);
            statement.setDate(3, dateStart);
            statement.setDate(4, dateEnd);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(rowMaper.mapRow(resultSet));
                }
            }

            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Excursion> findAvailableExcursions(Date dateStart, Date dateEnd) {
        String AVAILABLE_EXCURSIONS = "SELECT DISTINCT e.id, e.name FROM timetable AS tt " +
                "INNER JOIN excursion AS e " +
                "ON e.id = tt.excursion_id  " +
                "WHERE date_start BETWEEN ? AND ?";

        List<Excursion> excursions = new ArrayList<>();
        ExcursionRowMapper rowMapper = new ExcursionRowMapper();

        try (PreparedStatement statement = connection.prepareStatement(AVAILABLE_EXCURSIONS)) {
            statement.setDate(1, dateStart);
            statement.setDate(2, dateEnd);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    excursions.add(rowMapper.mapRow(resultSet));
                }
            }

            return excursions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Timetable objectToSave) {
        String SAVE_TIMETABLE = "INSERT INTO timetable(employee_id, excursion_id, date_start, date_end) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SAVE_TIMETABLE)) {
            statement.setLong(1, objectToSave.getEmployee().getId());
            statement.setLong(2, objectToSave.getExcursion().getId());
            statement.setDate(3, objectToSave.getDateStart());
            statement.setDate(4, objectToSave.getDateEnd());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String DELETE_TIMETABLE_BY_ID = "DELETE FROM timetable WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_TIMETABLE_BY_ID)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Timetable> findById(long id) {
        String DELETE_TIMETABLE_BY_ID = "SELECT * FROM timetable WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_TIMETABLE_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new TimetableRowMapper().mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Timetable> findAll() {
        String FIND_ALL_TIMETABLES = "SELECT * FROM timetable";
        List<Timetable> timetables = new ArrayList<>();
        TimetableRowMapper rowMapper = new TimetableRowMapper();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_TIMETABLES)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    timetables.add(rowMapper.mapRow(resultSet));
                }

                return timetables;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Timetable newObject) {
        String UPDATE_TIMETABLE = "UPDATE timetable set employee_id = ?, excursion_id = ?, date_start = ?," +
                "date_end = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TIMETABLE)) {
            statement.setLong(1, newObject.getEmployee().getId());
            statement.setLong(2, newObject.getExcursion().getId());
            statement.setDate(3, newObject.getDateStart());
            statement.setDate(4, newObject.getDateEnd());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
