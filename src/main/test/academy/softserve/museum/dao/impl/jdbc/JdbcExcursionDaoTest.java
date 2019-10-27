package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JdbcExcursionDaoTest extends JdbcDaoTest {
    private ExcursionDao excursionDao;
    private List<Excursion> excursions;

    @BeforeEach
    void init() {
        dropTables();
        createTables();
        fillTables();
        excursionDao = DaoFactory.excursionDao();

        excursions = new ArrayList<>(Arrays.asList(
                new Excursion(1, "Golden Spring"),
                new Excursion(2, "Da Vinci Demons"),
                new Excursion(3, "Inferno"),
                new Excursion(4, "Angels and Deamons")));
    }

    @Test
    void findAll() {
        assertExcursionEquals(excursions, excursionDao.findAll());
    }

    @Test
    void findByExistingId() {
        long id = 2;
        Excursion expected = excursions.get(1);

        assertExcursionEquals(expected, excursionDao.findById(id).orElse(null));
    }

    @Test
    void findByNotExistingId() {
        assertNull(excursionDao.findById(100500).orElse(null));
    }

    @Test
    public void updateExisting() {
        Excursion expected = new Excursion(4, "TEST");
        excursionDao.update(expected);

        Excursion actual = excursionDao.findById(4).orElse(null);

        assertExcursionEquals(expected, actual);
    }

    @Test
    public void updateNotExisting() {
        Excursion expected = new Excursion(100500, "TEST_FIRST_NAME");
        excursionDao.update(expected);
    }

    @Test
    void deleteByExistingId() {
        long id = 1;

        excursionDao.deleteById(id);

        List<Excursion> expected = excursions;
        excursions.remove(0);

        assertExcursionEquals(expected, excursionDao.findAll());
    }

    @Test
    void deleteByNotExistingId() {
        excursionDao.deleteById(100500);
    }

    @Test
    void save() {
        Excursion newExcursion = new Excursion(5, "TEST");

        assertEquals(5, excursionDao.save(newExcursion));

        assertExcursionEquals(newExcursion, excursionDao.findById(5).orElse(null));
    }

    @Test
    void findFullStatistic() {
        Map<Excursion, Integer> expectedExcursionCountMap = new LinkedHashMap<>();
        Date start = new java.sql.Date(LocalDateTime.of(2018, 7, 2, 12, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(2020, 7, 2, 14, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());

        expectedExcursionCountMap.put(excursions.get(0), 2);
        expectedExcursionCountMap.put(excursions.get(1), 2);
        expectedExcursionCountMap.put(excursions.get(2), 2);
        expectedExcursionCountMap.put(excursions.get(3), 2);

        ExcursionStatistic actual = excursionDao.findStatistic(start, end);

        assertEquals(start, actual.getDateStart());
        assertEquals(end, actual.getDateEnd());
        assertEquals(expectedExcursionCountMap, actual.getExcursionCountMap());
    }

    @Test
    void findEmptyStatistic() {
        Map<Excursion, Integer> expectedExcursionCountMap = new LinkedHashMap<>();
        Date start = new java.sql.Date(LocalDateTime.of(0, 7, 2, 12, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(10, 7, 2, 14, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());

        ExcursionStatistic actual = excursionDao.findStatistic(start, end);

        assertEquals(start, actual.getDateStart());
        assertEquals(end, actual.getDateEnd());
        assertEquals(expectedExcursionCountMap, actual.getExcursionCountMap());
    }

    @Test
    void findStatistic() {
        Map<Excursion, Integer> expectedExcursionCountMap = new LinkedHashMap<>();
        Date start = new java.sql.Date(LocalDateTime.of(2019, 7, 2, 12, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(2019, 7, 2, 14, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());

        expectedExcursionCountMap.put(excursions.get(0), 1);
        expectedExcursionCountMap.put(excursions.get(1), 1);
        expectedExcursionCountMap.put(excursions.get(2), 1);

        ExcursionStatistic actual = excursionDao.findStatistic(start, end);

        assertEquals(start, actual.getDateStart());
        assertEquals(end, actual.getDateEnd());
        assertEquals(expectedExcursionCountMap, actual.getExcursionCountMap());
    }

    @Test
    void findByExistingName() {
        String name = "Angels and Deamons";
        Excursion expected = excursions.get(3);

        assertExcursionEquals(expected, excursionDao.findByName(name).orElse(null));
    }

    @Test
    void findByNotExistingName() {
        String name = "TEST";

        assertNull(excursionDao.findByName(name).orElse(null));
    }

    @Test
    void findAllAvailable() {
        Date start = new java.sql.Date(LocalDateTime.of(2018, 7, 2, 12, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(2020, 7, 2, 14, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());

        List<Excursion> expected = excursions;

        assertExcursionEquals(expected, excursionDao.findAvailable(start, end));
    }

    @Test
    void findNoneAvailable() {
        Date start = new java.sql.Date(LocalDateTime.of(1, 7, 2, 12, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(10, 7, 2, 14, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());

        assertExcursionEquals(Collections.emptyList(), excursionDao.findAvailable(start, end));
    }

    @Test
    void findFewAvailable() {
        Date start = new java.sql.Date(LocalDateTime.of(2019, 7, 3, 14, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());
        Date end = new Date(LocalDateTime.of(2019, 7, 3, 16, 1, 1).
                toInstant(ZoneOffset.of("+03:00")).toEpochMilli());

        List<Excursion> expected = Arrays.asList(
                excursions.get(0),
                excursions.get(1),
                excursions.get(3));

        assertExcursionEquals(expected, excursionDao.findAvailable(start, end));
    }

}
