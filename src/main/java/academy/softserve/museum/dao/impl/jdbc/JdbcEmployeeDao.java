package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeStatisticRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.IdRowMapper;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class, that implements special methods for
 * getting / updating Employee objects from / in database.
 */
public class JdbcEmployeeDao implements EmployeeDao {

    /**
     * this field used for loading and setting Audience for Employee.
     */
    private AudienceDao audienceDao;
    /**
     * Connection used for interaction with database.
     */
    private final Connection connection;
    /**
     * this field used for implementing Singleton.
     */
    private static JdbcEmployeeDao instance;

    /**
     * Constructor, that creates instance using Connection.
     *
     * @param connection Connection used for interaction with database.
     */
    private JdbcEmployeeDao(Connection connection, AudienceDao audienceDao) {
        this.connection = connection;
        this.audienceDao = audienceDao;
    }

    /**
     * Method for getting instance of JdbcEmployeeDao.
     *
     * @param connection Connection, that used for interaction with database.
     * @return instance of JdbcEmployeeDao.
     */
    public static JdbcEmployeeDao getInstance(Connection connection, AudienceDao audienceDao) {
        if (instance == null) {
            instance = new JdbcEmployeeDao(connection, audienceDao);
        }

        return instance;
    }

    /**
     * Method for finding Employees by position.
     * Method doesn't return Employee with loaded Audience.
     * To load audience you need to call loadForeignFields method.
     *
     * @param position position of Employee.
     * @return list of Employees with given position.
     */
    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        String FIND_BY_POSITION = "SELECT id AS employee_id, first_name AS employee_first_name, " +
                "last_name AS employee_last_name, position AS employee_position, login AS employee_login, " +
                "password AS employee_password FROM employees WHERE position = ?";

        return JdbcUtils.query(connection, FIND_BY_POSITION, new EmployeeRowMapper(), position.toString());
    }

    /**
     * Method for finding Employees by username (login).
     * Method doesn't return Employee with loaded Audience.
     * To load audience you need to call loadForeignFields method.
     *
     * @param username username (login) of Employee.
     * @return Employee object wrapped in Optional or
     * Optional.empty() if there is no Employee with
     * such username (login).
     */
    @Override
    public Optional<Employee> findByUsername(String username) {
        String FIND_EMPLOYEE_BY_USERNAME = "SELECT id AS employee_id, first_name AS employee_first_name, " +
                "last_name AS employee_last_name, position AS employee_position, login AS employee_login, " +
                "password AS employee_password FROM employees WHERE login = ?";

        return JdbcUtils.queryForObject(connection, FIND_EMPLOYEE_BY_USERNAME, new EmployeeRowMapper(), username);
    }

    /**
     * Method for finding Employee by full name.
     * Method doesn't return Employee with loaded Audience.
     * To load audience you need to call loadForeignFields method.
     *
     * @param firstName Employee's first name.
     * @param lastName  Employee's last name.
     * @return Employee object wrapped in Optional or
     * Optional.empty() if there is no Employee with
     * such full name.
     */
    @Override
    public Optional<Employee> findByFullName(String firstName, String lastName) {
        String FIND_EMPLOYEE_BY_USERNAME = "SELECT id AS employee_id, first_name AS employee_first_name, " +
                "last_name AS employee_last_name, position AS employee_position, login AS employee_login, " +
                "password AS employee_password FROM employees WHERE first_name = ? and last_name = ?";

        return JdbcUtils.queryForObject(connection, FIND_EMPLOYEE_BY_USERNAME, new EmployeeRowMapper(),
                firstName, lastName);
    }

    /**
     * Method for finding statistic for all Employees.
     * Statistic contains work time in hours and excursions count
     * for each Employee.
     *
     * @param dateStart date and time for statistic calculating start.
     * @param dateEnd   date and time for statistic calculating end.
     * @return statistic.
     */
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

    /**
     * Method for finding available tour guides in some period.
     * Tour guide is available if he / she hasn't any excursion
     * in given period.
     *
     * @param dateStart period start date and time.
     * @param dateEnd   period end date and time.
     * @return list of available Employees.
     */
    @Override
    public List<Employee> findAvailable(Date dateStart, Date dateEnd) {
        String FIND_AVAILABLE_TOUR_GUIDES =
                "SELECT DISTINCT e.id AS employee_id, e.first_name AS employee_first_name, e.last_name AS employee_last_name, " +
                        "e.position AS employee_position, e.login AS employee_login, e.password AS employee_password " +
                        "FROM employees AS e " +
                        "WHERE e.id NOT IN(" +
                        "SELECT employee_id FROM timetable " +
                        "WHERE date_start BETWEEN ? AND ? " +
                        "OR date_end BETWEEN ? AND ? " +
                        "OR (date_start < ? AND date_end > ?))";

        return JdbcUtils.query(connection, FIND_AVAILABLE_TOUR_GUIDES, new EmployeeRowMapper(), dateStart, dateEnd,
                dateStart, dateEnd, dateStart, dateEnd);
    }

    /**
     * Method for updating Audience that Employee is
     * responsible for. Given Employee and Audience objects must have
     * existing id.
     *
     * @param employee Employee for which you want to update Audience.
     * @param audience new Audience.
     */
    @Override
    public void updateAudience(Employee employee, Audience audience) {
        String UPDATE_EMPLOYEE_AUDIENCE = "UPDATE employees SET audience_id = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_EMPLOYEE_AUDIENCE, audience.getId(), employee.getId());
    }

    /**
     * Method for loading and setting Employee's Audience.
     *
     * @param employee Employee for which you want
     *                 to load and set Audience.
     * @return Given Employee with set Audience.
     */
    @Override
    public Employee loadForeignFields(Employee employee) {
        employee.setAudience(audienceDao.findByEmployee(employee).orElse(null));

        return employee;
    }

    /**
     * Method for saving objects in database.
     * This method ignore audience field.
     * To link Audience with Employee you should
     * call updateAudience().
     *
     * @param objectToSave object, that must be saved.
     * @return id of last saved object.
     */
    @Override
    public long save(Employee objectToSave) {
        String SAVE_EMPLOYEE = "INSERT INTO employees(first_name, last_name, position, login, password) " +
                "VALUES (?, ?, ?, ?, ?)";

        JdbcUtils.update(connection, SAVE_EMPLOYEE, objectToSave.getFirstName(), objectToSave.getLastName(),
                objectToSave.getPosition().toString(), objectToSave.getLogin(), objectToSave.getPassword());

        return getLastSavedObjectId();
    }

    /**
     * Method for deleting object by id.
     *
     * @param id id of object, that must be deleted.
     */
    @Override
    public void deleteById(long id) {
        String DELETE_EMPLOYEE = "DELETE FROM employees WHERE id = ?";

        JdbcUtils.update(connection, DELETE_EMPLOYEE, id);
    }

    /**
     * Method, that returns object wrapped in Optional by id.
     * Method doesn't return Employee with loaded Audience.
     * To load audience you need to call loadForeignFields method.
     *
     * @param id object's id.
     * @return object wrapped in Optional that has id field value
     * the same as id parameter value.
     * If there is no object with such id value
     * it returns Optional.empty
     */
    @Override
    public Optional<Employee> findById(long id) {
        String FIND_EMPLOYEE_BY_ID = "SELECT id AS employee_id, first_name AS employee_first_name, last_name AS employee_last_name, " +
                "position AS employee_position, login AS employee_login, password AS employee_password FROM employees WHERE id = ?";
        return JdbcUtils.queryForObject(connection, FIND_EMPLOYEE_BY_ID, new EmployeeRowMapper(), id);
    }

    /**
     * Method, that returns all Employee objects from database.
     *
     * @return list of Employees from database.
     */
    @Override
    public List<Employee> findAll() {
        String FIND_ALL_EMPLOYEE = "SELECT id AS employee_id, first_name AS employee_first_name, last_name AS employee_last_name, " +
                "position AS employee_position, login AS employee_login, password AS employee_password FROM employees";

        return JdbcUtils.query(connection, FIND_ALL_EMPLOYEE, new EmployeeRowMapper());
    }

    /**
     * Method, that updates given object in database.
     * This method ignore audience field.
     * To link Audience with Employee you should
     * call updateAudience().
     *
     * @param newObject object to update
     * @return changed rows count in database.
     */
    @Override
    public int update(Employee newObject) {
        String UPDATE_EMPLOYEE = "UPDATE employees set first_name = ?, last_name = ?, position = ?, " +
                "login = ?, password = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_EMPLOYEE, newObject.getFirstName(), newObject.getLastName(),
                newObject.getPosition().toString(), newObject.getLogin(), newObject.getPassword(), newObject.getId());
    }

    /**
     * Method for getting last saved Employee id.
     *
     * @return id of last saved Employee.
     */
    private long getLastSavedObjectId() {
        String QUERY = "SELECT MAX(id) AS last_id FROM employees";

        return JdbcUtils.queryForObject(connection, QUERY, new IdRowMapper()).
                orElseThrow(() -> new RuntimeException("Can't get last saved object id"));
    }
}
