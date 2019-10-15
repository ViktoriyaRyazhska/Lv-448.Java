package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AudienceRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeRowMaper;
import academy.softserve.museum.dao.impl.jdbc.mappers.EmployeeStatisticRowMapper;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class JdbcEmployeeDao implements EmployeeDao {
    private final Connection connection;

    public JdbcEmployeeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        String FIND_BY_POSITION = "SELECT * FROM employees WHERE position = ?";
        List<Employee> employees = new ArrayList<>();
        EmployeeRowMaper rowMaper = new EmployeeRowMaper();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_POSITION)) {
            statement.setString(1, position.toString());

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    employees.add(rowMaper.mapRow(resultSet));
                }

                return employees;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee findByUsername(String username) {
        String FIND_EMPLOYEE_BY_USERNAME = "SELECT * FROM employees WHERE login = ?";

        try (PreparedStatement statement = connection.prepareStatement(FIND_EMPLOYEE_BY_USERNAME)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new EmployeeRowMaper().mapRow(resultSet);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeStatistic findStatistic(Date dateStart, Date dateEnd) {
        String FIND_STATISTIC =
                "SELECT e.id, e.first_name, e.last_name, e.position, e.login, e.password, " +
                        "SUM(DATE_PART('hour', date_end - date_start) * 60 + " +
                        "DATE_PART('minute', date_end - date_start)) AS excursion_time\n" +
                        "FROM employees AS e " +
                        "INNER JOIN timetable AS tt " +
                        "ON e.id = tt.employee_id " +
                        "WHERE date_start > ? and date_end < ? " +
                        "GROUP BY e.id, e.first_name, e.last_name, e.position, e.login, e.password; ";

        EmployeeStatisticRowMapper employeeStatisticRowMapper = new EmployeeStatisticRowMapper();
        EmployeeStatistic employeeStatistic;

        try (PreparedStatement statement = connection.prepareStatement(FIND_STATISTIC)) {
            statement.setDate(1, dateStart);
            statement.setDate(2, dateEnd);

            try (ResultSet resultSet = statement.executeQuery()) {
                employeeStatistic = employeeStatisticRowMapper.mapRow(resultSet);
                employeeStatistic.setDateStart(dateStart);
                employeeStatistic.setDateEnd(dateEnd);

                return employeeStatistic;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Audience findAudienceByEmployee(Employee employee) {
        String FIND_AUDIENCE_BY_EMPLOYEE = "SELECT audiences.id as audience_id, " +
                "name as audience_name FROM audiences INNER JOIN employees " +
                "ON employees.audience_id = audiences.id";

        try (PreparedStatement statement = connection.prepareStatement(FIND_AUDIENCE_BY_EMPLOYEE)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new AudienceRowMapper().mapRow(resultSet);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEmployeeAudience(Employee employee, Audience audience) {
        String UPDATE_EMPLOYEE_AUDIENCE = "UPDATE employees SET audience_id = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_AUDIENCE)) {
            statement.setLong(1, audience.getId());
            statement.setLong(2, employee.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Employee objectToSave) {
        String SAVE_EMPLOYEE = "INSERT INTO employees(first_name, last_name, position, login, password) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SAVE_EMPLOYEE)) {
            statement.setString(1, objectToSave.getFirstName());
            statement.setString(2, objectToSave.getLastName());
            statement.setString(3, objectToSave.getPosition().toString());
            statement.setString(4, objectToSave.getLogin());
            statement.setString(5, objectToSave.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String SAVE_EMPLOYEE = "DELETE FROM employees WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(SAVE_EMPLOYEE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Employee> findById(long id) {
        String FIND_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(FIND_EMPLOYEE_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new EmployeeRowMaper().mapRow(resultSet));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> findAll() {
        String FIND_ALL_EMPLOYEE = "SELECT * FROM employees";
        List<Employee> employees = new ArrayList<>();
        EmployeeRowMaper rowMaper = new EmployeeRowMaper();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_EMPLOYEE)) {
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
    public void update(Employee newObject) {
        String UPDATE_EMPLOYEE = "UPDATE employees set first_name = ?, last_name = ?, position = ?, " +
                "login = ?, password = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE)) {
            statement.setString(1, newObject.getFirstName());
            statement.setString(2, newObject.getLastName());
            statement.setString(3, newObject.getPosition().toString());
            statement.setString(4, newObject.getLogin());
            statement.setString(5, newObject.getPassword());
            statement.setLong(6, newObject.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
