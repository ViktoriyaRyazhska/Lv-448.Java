package academy.softserve.dao.impl.jdbc;

import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcTimetableDao;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

public class JdbcTimetableDaoTest extends JdbcDaoTest {
    private JdbcTimetableDao timetableDao;
    private ExcursionDao excursionDao;
    private EmployeeDao employeeDao;
    private List<Timetable> timetables;

    @BeforeEach
    void init() {
        dropTables();
        createTables();
        fillTables();
        timetableDao = jdbcTimetableDao();
        excursionDao = jdbcExcursionDao();
        employeeDao = jdbcEmployeeDao();

        timetables = new ArrayList<>(Arrays.asList(
                new Timetable(1, employeeDao.findById(1).orElse(null), excursionDao.findById(1).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-02T12:10:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-02T13:10:00"))),
                new Timetable(2, employeeDao.findById(2).orElse(null), excursionDao.findById(2).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-02T12:10:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-02T13:10:00"))),
                new Timetable(3, employeeDao.findById(3).orElse(null), excursionDao.findById(3).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-02T12:10:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-02T13:10:00"))),
                new Timetable(4, employeeDao.findById(4).orElse(null), excursionDao.findById(4).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-03T14:10:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-03T15:10:00"))),
                new Timetable(5, employeeDao.findById(5).orElse(null), excursionDao.findById(1).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-03T14:30:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-03T15:30:00"))),
                new Timetable(6, employeeDao.findById(6).orElse(null), excursionDao.findById(2).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-03T14:30:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-03T15:30:00"))),
                new Timetable(7, employeeDao.findById(7).orElse(null), excursionDao.findById(3).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-04T19:30:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-04T20:30:00"))),
                new Timetable(8, employeeDao.findById(5).orElse(null), excursionDao.findById(4).orElse(null),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-05T17:30:00")),
                        localDateTimeToDate(LocalDateTime.parse("2019-07-05T18:30:00")))));

    }

    @Test
    void findAll() {
        assertTimetableEquals(timetables, timetableDao.findAll());
    }

    @Test
    void findByExistingId() {
        long id = 5;
        Timetable expected = timetables.get(4);

        assertTimetableEquals(expected, timetableDao.findById(id).orElse(null));
    }

    @Test
    void findByNotExistingId() {
        assertNull(timetableDao.findById(100500).orElse(null));
    }

    @Test
    public void updateExisting() {
        Timetable expected = timetables.get(0);
        expected.setExcursion(new Excursion(3, "Inferno"));

        timetableDao.update(expected);

        Timetable actual = timetableDao.findById(1).orElse(null);

        assertTimetableEquals(expected, actual);
    }

    @Test
    public void updateNotExisting() {
        Timetable timetable = new Timetable(100500, new Employee(), new Excursion(), null, null);
        timetableDao.update(timetable);

        assertTimetableEquals(timetables, timetableDao.findAll());
    }

    @Test
    void deleteByExistingId() {
        long id = 1;

        timetableDao.deleteById(id);

        List<Timetable> expected = timetables;
        expected.remove(0);

        assertTimetableEquals(expected, timetableDao.findAll());
    }

    @Test
    void deleteByNotExistingId() {
        timetableDao.deleteById(100500);
    }

    @Test
    void save() {
        Timetable newTimetable = new Timetable(9, employeeDao.findById(1).orElse(null), excursionDao.findById(1).orElse(null),
                localDateTimeToDate(LocalDateTime.parse("2019-07-05T17:30:00")),
                localDateTimeToDate(LocalDateTime.parse("2019-07-05T17:30:00")));

        timetableDao.save(newTimetable);

        assertTimetableEquals(newTimetable, timetableDao.findById(9).orElse(null));
    }

    private Date localDateTimeToDate(LocalDateTime dateTime) {
        return new Date(dateTime.toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
    }
}
