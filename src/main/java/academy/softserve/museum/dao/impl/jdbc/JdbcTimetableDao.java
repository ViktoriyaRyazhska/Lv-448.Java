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
    public long save(Timetable objectToSave) {
        String SAVE_TIMETABLE = "INSERT INTO timetable(employee_id, excursion_id, date_start, date_end) " +
                "VALUES (?, ?, ?, ?)";

        return JdbcUtils.update(connection, SAVE_TIMETABLE, objectToSave.getEmployee().getId(), objectToSave.getExcursion().getId(),
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
    public int update(Timetable newObject) {
        String UPDATE_TIMETABLE = "UPDATE timetable set employee_id = ?, excursion_id = ?, date_start = ?," +
                "date_end = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_TIMETABLE, newObject.getEmployee().getId(), newObject.getExcursion().getId(),
                newObject.getDateStart(), newObject.getDateEnd(), newObject.getId());
    }
}
