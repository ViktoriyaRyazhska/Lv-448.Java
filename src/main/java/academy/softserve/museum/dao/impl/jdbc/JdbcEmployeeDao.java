package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeRowMaper;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeStatisticRowMapper;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class JdbcEmployeeDao implements EmployeeDao {
    private final Connection connection;
    private AudienceDao audienceDao;
    private static JdbcEmployeeDao instance;

    private JdbcEmployeeDao(Connection connection, AudienceDao audienceDao) {
        this.connection = connection;
        this.audienceDao = audienceDao;
    }

    public static JdbcEmployeeDao getInstance(Connection connection, AudienceDao audienceDao) {
        if (instance == null) {
            instance = new JdbcEmployeeDao(connection, audienceDao);
        }

        return instance;
    }

    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        String FIND_BY_POSITION = "SELECT id AS employee_id, first_name AS employee_first_name, " +
                "last_name AS employee_last_name, position AS employee_position, login AS employee_login, " +
                "password AS employee_password FROM employees WHERE position = ?";

        return JdbcUtils.query(connection, FIND_BY_POSITION, new EmployeeRowMaper(), position.toString());
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        String FIND_EMPLOYEE_BY_USERNAME = "SELECT id AS employee_id, first_name AS employee_first_name, " +
                "last_name AS employee_last_name, position AS employee_position, login AS employee_login, " +
                "password AS employee_password FROM employees WHERE login = ?";

        return JdbcUtils.queryForObject(connection, FIND_EMPLOYEE_BY_USERNAME, new EmployeeRowMaper(), username);
    }

    @Override
    public Optional<Employee> findByFullName(String firstName, String lastName) {
        String FIND_EMPLOYEE_BY_USERNAME = "SELECT id AS employee_id, first_name AS employee_first_name, " +
                "last_name AS employee_last_name, position AS employee_position, login AS employee_login, " +
                "password AS employee_password FROM employees WHERE first_name = ? and last_name = ?";

        return JdbcUtils.queryForObject(connection, FIND_EMPLOYEE_BY_USERNAME, new EmployeeRowMaper(),
                firstName, lastName);
    }

    @Override
    public EmployeeStatistic findStatistic(Date dateStart, Date dateEnd) {
        String FIND_STATISTIC =
                "SELECT e.id AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name," +
                        "e.position AS employee_position, e.login AS employee_login, e.password AS employee_password," +
                        "SUM(date_part('epoch', tt.date_end - tt.date_start) / 60) AS excursion_time, " +
                        "COUNT(*) AS excursion_count " +
                        "FROM employees AS e " +
                        "LEFT JOIN timetable AS tt " +
                        "ON e.id = tt.employee_id " +
                        "WHERE (date_start > ? AND date_end < ?) " +
                        "GROUP BY e.id, e.first_name, e.last_name, e.position, e.login, e.password " +
                        "union " +
                        "SELECT e.id AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name, " +
                        "e.position AS employee_position, e.login AS employee_login, e.password AS employee_password, " +
                        "0 AS excursion_time, " +
                        "0 AS excursion_count " +
                        "FROM employees AS e " +
                        "LEFT JOIN timetable AS tt " +
                        "ON e.id = tt.id " +
                        "where e.id not in (select employee_id from timetable) " +
                        "GROUP BY e.id, e.first_name, e.last_name, e.position, e.login, e.password";

        EmployeeStatistic statistic = JdbcUtils.queryForObject(connection, FIND_STATISTIC,
                new EmployeeStatisticRowMapper(), dateStart, dateEnd).orElse(new EmployeeStatistic());

        statistic.setDateStart(dateStart);
        statistic.setDateEnd(dateEnd);

        return statistic;
    }

    @Override
    public List<Employee> findAvailable(Date dateStart, Date dateEnd) {
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

    @Override
    public void updateAudience(Employee employee, Audience audience) {
        String UPDATE_EMPLOYEE_AUDIENCE = "UPDATE employees SET audience_id = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_EMPLOYEE_AUDIENCE, audience.getId(), employee.getId());
    }

    @Override
    public Employee loadForeignFields(Employee employee) {
        employee.setAudience(audienceDao.findByEmployee(employee).orElse(null));

        return employee;
    }

    @Override
    public long save(Employee objectToSave) {
        String SAVE_EMPLOYEE = "INSERT INTO employees(first_name, last_name, position, login, password) " +
                "VALUES (?, ?, ?, ?, ?)";

        return JdbcUtils.update(connection, SAVE_EMPLOYEE, objectToSave.getFirstName(), objectToSave.getLastName(),
                objectToSave.getPosition().toString(), objectToSave.getLogin(), objectToSave.getPassword());
    }

    @Override
    public void deleteById(long id) {
        String SAVE_EMPLOYEE = "DELETE FROM employees WHERE id = ?";

        JdbcUtils.update(connection, SAVE_EMPLOYEE, id);
    }

    @Override
    public Optional<Employee> findById(long id) {
        String FIND_EMPLOYEE_BY_ID = "SELECT id AS employee_id, first_name AS employee_first_name, last_name AS employee_last_name, " +
                "position AS employee_position, login AS employee_login, password AS employee_password FROM employees WHERE id = ?";
        return JdbcUtils.queryForObject(connection, FIND_EMPLOYEE_BY_ID, new EmployeeRowMaper(), id);
    }

    @Override
    public List<Employee> findAll() {
        String FIND_ALL_EMPLOYEE = "SELECT id AS employee_id, first_name AS employee_first_name, last_name AS employee_last_name, " +
                "position AS employee_position, login AS employee_login, password AS employee_password FROM employees";

        return JdbcUtils.query(connection, FIND_ALL_EMPLOYEE, new EmployeeRowMaper());
    }

    @Override
    public int update(Employee newObject) {
        String UPDATE_EMPLOYEE = "UPDATE employees set first_name = ?, last_name = ?, position = ?, " +
                "login = ?, password = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_EMPLOYEE, newObject.getFirstName(), newObject.getLastName(),
                newObject.getPosition().toString(), newObject.getLogin(), newObject.getPassword(), newObject.getId());
    }
}
