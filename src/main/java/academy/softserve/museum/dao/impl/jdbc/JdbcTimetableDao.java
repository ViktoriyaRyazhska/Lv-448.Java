package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.TimetableDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeRowMaper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.TimetableRowMapper;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JdbcTimetableDao implements TimetableDao {

    private static JdbcTimetableDao instance;
    private final Connection connection;

    private JdbcTimetableDao(Connection connection) {
        this.connection = connection;
    }

    public static JdbcTimetableDao getInstance(Connection connection) {
        if(instance == null){
            instance = new JdbcTimetableDao(connection);
        }

        return instance;
    }

    @Override
    public List<Employee> findAvailableTourGuides(Date dateStart, Date dateEnd) {
        String FIND_AVAILABLE_TOUR_GUIDES =
                "SELECT DISTINCT e.id AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name, " +
                        "e.position AS employee_position, e.login AS employee_login, e.password AS employee_password " +
                        "FROM employees AS e " +
                        "WHERE e.id NOT IN(" +
                        "SELECT employee_id FROM timetable " +
                        "WHERE date_start BETWEEN ? AND ? " +
                        "OR date_end BETWEEN ? AND ?)";

        return JdbcUtils.query(connection, FIND_AVAILABLE_TOUR_GUIDES, new EmployeeRowMaper(), dateStart, dateEnd,
                dateStart, dateEnd);
    }

    // TODO Shoud return list of Timetable
    @Override
    public List<Excursion> findAvailableExcursions(Date dateStart, Date dateEnd) {
        String AVAILABLE_EXCURSIONS =
                "SELECT DISTINCT e.id AS excursion_id, e.name AS excursion_name " +
                        "FROM timetable AS tt " +
                        "INNER JOIN excursion AS e " +
                        "ON e.id = tt.excursion_id  " +
                        "WHERE date_start BETWEEN ? AND ?";

        return JdbcUtils.query(connection, AVAILABLE_EXCURSIONS, new ExcursionRowMapper(), dateStart, dateEnd);
    }

    @Override
    public void save(Timetable objectToSave) {
        String SAVE_TIMETABLE = "INSERT INTO timetable(employee_id, excursion_id, date_start, date_end) " +
                "VALUES (?, ?, ?, ?)";

        JdbcUtils.update(connection, SAVE_TIMETABLE, objectToSave.getEmployee().getId(), objectToSave.getExcursion().getId(),
                objectToSave.getDateStart(), objectToSave.getDateEnd());
    }

    @Override
    public void deleteById(long id) {
        String DELETE_TIMETABLE_BY_ID = "DELETE FROM timetable WHERE id = ?";

        JdbcUtils.update(connection, DELETE_TIMETABLE_BY_ID, id);
    }

    @Override
    public Optional<Timetable> findById(long id) {
        String FIND_TIMETABLE_BY_ID = "SELECT id AS timetable_id, employee_id, excursion_id, date_start, date_end " +
                "FROM timetable WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_TIMETABLE_BY_ID, new TimetableRowMapper(), id);
    }

    @Override
    public List<Timetable> findAll() {
        String FIND_ALL_TIMETABLES = "SELECT id AS timetable_id, employee_id, excursion_id, date_start, date_end FROM timetable";

        return JdbcUtils.query(connection, FIND_ALL_TIMETABLES, new TimetableRowMapper());
    }

    @Override
    public void update(Timetable newObject) {
        String UPDATE_TIMETABLE = "UPDATE timetable set employee_id = ?, excursion_id = ?, date_start = ?," +
                "date_end = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_TIMETABLE, newObject.getEmployee().getId(), newObject.getExcursion().getId(),
                newObject.getDateStart(), newObject.getDateEnd(), newObject.getId());
    }
}
