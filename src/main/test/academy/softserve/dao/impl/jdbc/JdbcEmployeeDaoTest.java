package academy.softserve.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcEmployeeDaoTest extends JdbcDaoTest {
    private EmployeeDao employeeDao;
    private AudienceDao audienceDao;
    private List<Employee> employees;

    @BeforeEach
    void init() {
        dropTables();
        createTables();
        fillTables();
        employeeDao = jdbcEmployeeDao();
        audienceDao = jdbcAudienceDao();

        employees = new ArrayList<>(Arrays.asList(
                new Employee(1, "Anna", "Kentor", EmployeePosition.MANAGER, "a_kentor", "anna1230"),
                new Employee(2, "Bogdan", "Korty", EmployeePosition.AUDIENCE_MANAGER, "Bogdan_Korty", "_Korty59"),
                new Employee(3, "Bill", "Kell", EmployeePosition.AUDIENCE_MANAGER, "Kell_007", "Bond_007"),
                new Employee(4, "Jeck", "Loper", EmployeePosition.AUDIENCE_MANAGER, "loper_79", "123456qwe"),
                new Employee(5, "Tom", "Vagik", EmployeePosition.TOUR_GUIDE, "Vagik2005", "1Best"),
                new Employee(6, "Angela", "Sadik", EmployeePosition.TOUR_GUIDE, "angel", "IamAngel"),
                new Employee(7, "Julia", "Karenge", EmployeePosition.TOUR_GUIDE, "Karenge_j", "hello_world")));
    }

    @Test
    void save() {
        Employee employee = new Employee(8, "TEST", "TEST", EmployeePosition.MANAGER, "TEST", "TEST");

        employeeDao.save(employee);

        assertEmployeeEquals(employee, employeeDao.findById(8).orElse(null));
    }

    @Test
    void deleteByExistingId() {
        long id = 3;
        List<Employee> expected = employees;

        expected.remove(2);
        employeeDao.deleteById(id);

        assertEmployeeEquals(expected, employeeDao.findAll());
    }

    @Test
    void deleteByNotExistingId() {
        long id = 100500;
        List<Employee> expected = employees;

        employeeDao.deleteById(id);

        assertEmployeeEquals(expected, employeeDao.findAll());
    }

    @Test
    void findByExistingId() {
        long id = 7;
        Employee expected = employees.get(6);

        assertEmployeeEquals(expected, employeeDao.findById(id).orElse(null));
    }

    @Test
    void findByNotExistingId() {
        long id = 100500;

        assertNull(employeeDao.findById(id).orElse(null));
    }

    @Test
    void findAll() {
        assertEmployeeEquals(employees, employeeDao.findAll());
    }

    @Test
    void updateExisting() {
        Employee expected = new Employee(1, "UPDATED", "UPDATED",
                EmployeePosition.TOUR_GUIDE, "UPDATED", "UPDATED");

        employeeDao.update(expected);

        assertEmployeeEquals(expected, employeeDao.findById(1).orElse(null));
    }

    @Test
    void updateNotExisting() {
        Employee expected = new Employee(100500, "UPDATED", "UPDATED",
                EmployeePosition.TOUR_GUIDE, "UPDATED", "UPDATED");
        employeeDao.update(expected);
    }

    @Test
    void findByPosition() {
        EmployeePosition position = EmployeePosition.TOUR_GUIDE;
        List<Employee> expected = employees.stream().
                filter(a -> a.getPosition().equals(position)).
                collect(Collectors.toList());

        assertEmployeeEquals(expected, employeeDao.findByPosition(position));
    }

    @Test
    void findByExistingUsername() {
        String username = "loper_79";
        Employee expected = employees.get(3);

        assertEmployeeEquals(expected, employeeDao.findByUsername(username).orElse(null));
    }

    @Test
    void findByNotExistingUsername() {
        String username = "TEST";

        assertNull(employeeDao.findByUsername(username).orElse(null));
    }

    @Test
    void findByExistingFullName() {
        String firstName = "Julia";
        String lastName = "Karenge";
        Employee expected = employees.get(6);

        assertEmployeeEquals(expected, employeeDao.findByFullName(firstName, lastName).orElse(null));
    }

    @Test
    void findByNotExistingFullName() {
        String firstName = "TEST";
        String lastName = "TEST";

        assertNull(employeeDao.findByFullName(firstName, lastName).orElse(null));
    }

    @Test
    void findFullStatistic() {
        EmployeeStatistic expected = new EmployeeStatistic();
        Map<Employee, Integer> workTimeMap = new LinkedHashMap<>();
        Map<Employee, Integer> excursionCountMap = new LinkedHashMap<>();
        Date start = new Date(LocalDateTime.of(2000, 1, 1, 1, 1, 1).
                toInstant(ZoneOffset.of("+02:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(2020, 1, 1, 1, 1, 1).
                toInstant(ZoneOffset.of("+02:00")).toEpochMilli());

        workTimeMap.put(employees.get(0), 60);
        workTimeMap.put(employees.get(1), 60);
        workTimeMap.put(employees.get(2), 60);
        workTimeMap.put(employees.get(3), 60);
        workTimeMap.put(employees.get(4), 120);
        workTimeMap.put(employees.get(5), 60);
        workTimeMap.put(employees.get(6), 60);

        excursionCountMap.put(employees.get(0), 1);
        excursionCountMap.put(employees.get(1), 1);
        excursionCountMap.put(employees.get(2), 1);
        excursionCountMap.put(employees.get(3), 1);
        excursionCountMap.put(employees.get(4), 2);
        excursionCountMap.put(employees.get(5), 1);
        excursionCountMap.put(employees.get(6), 1);

        expected.setExcursionCount(excursionCountMap);
        expected.setWorkTimeMap(workTimeMap);

        EmployeeStatistic actual = employeeDao.findStatistic(start, end);

        assertEquals(workTimeMap, actual.getWorkTimeMap());
        assertEquals(excursionCountMap, actual.getExcursionCount());
    }

    @Test
    void findEmptyStatistic() {
        EmployeeStatistic expected = new EmployeeStatistic();
        Map<Employee, Integer> workTimeMap = new LinkedHashMap<>();
        Map<Employee, Integer> excursionCountMap = new LinkedHashMap<>();
        Date start = new Date(LocalDateTime.of(1, 1, 1, 1, 1, 1).
                toInstant(ZoneOffset.of("+02:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(10, 1, 1, 1, 1, 1).
                toInstant(ZoneOffset.of("+02:00")).toEpochMilli());

        expected.setExcursionCount(excursionCountMap);
        expected.setWorkTimeMap(workTimeMap);

        EmployeeStatistic actual = employeeDao.findStatistic(start, end);

        assertEquals(workTimeMap, actual.getWorkTimeMap());
        assertEquals(excursionCountMap, actual.getExcursionCount());
    }

    @Test
    void findAllAvailable() {
        Date start = new Date(LocalDateTime.of(2020, 7, 2, 12, 0, 0).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(2021, 7, 2, 14, 0, 0).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        List<Employee> expected = Arrays.asList(employees.get(6), employees.get(5), employees.get(2), employees.get(4),
                employees.get(1), employees.get(3), employees.get(0));


        assertEmployeeEquals(expected, employeeDao.findAvailable(start, end));
    }

    @Test
    void findFewAvailable() {
        Date start = new Date(LocalDateTime.of(2019, 7, 2, 12, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(2019, 7, 2, 14, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        List<Employee> expected = Arrays.asList(employees.get(6), employees.get(5), employees.get(4), employees.get(3));

        assertEmployeeEquals(expected, employeeDao.findAvailable(start, end));
    }

    @Test
    void updateEmployeeExistingAudience() {
        Employee employee = employees.get(1);
        Audience newAudience = new Audience(4, "TEST");

        audienceDao.save(newAudience);
        employeeDao.updateAudience(employee, newAudience);

        assertAudienceEquals(newAudience, audienceDao.findByEmployee(employee).orElse(null));
    }

    @Test
    void updateEmployeeNotExistingAudience() {
        Employee employee = employees.get(1);
        Audience newAudience = new Audience(100500, "TEST");

        assertThrows(RuntimeException.class, () -> employeeDao.updateAudience(employee, newAudience));
    }

    @Test
    void loadForeignFieldsForAudienceManager() {
        Employee expected = new Employee(2, "Bogdan", "Korty",
                EmployeePosition.AUDIENCE_MANAGER, "Bogdan_Korty", "_Korty59");
        Audience expectedAudience = new Audience(1, "Leonardo da Vinci");
        Employee actual = employees.get(1);

        employeeDao.loadForeignFields(actual);

        assertEmployeeEquals(expected, actual);
        assertAudienceEquals(expectedAudience, actual.getAudience());
    }

}
