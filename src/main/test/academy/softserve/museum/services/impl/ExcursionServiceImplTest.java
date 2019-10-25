package academy.softserve.museum.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.ExcursionService;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ExcursionServiceImplTest {

    @Mock
    private ExcursionDao mock;
    @InjectMocks
    private ExcursionService service = new ExcursionServiceImpl();

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void saveExisting() {
        Optional<Excursion> excursion = Optional.of(new Excursion());

        when(mock.findByName(any())).thenReturn(excursion);
        assertThrows(NotSavedException.class, () -> service.save(new Excursion()));
    }

    @Test
    void saveNotExisting() {
        Optional<Excursion> excursion = Optional.empty();

        when(mock.findByName(any())).thenReturn(excursion);
        assertTrue(service.save(new Excursion()));
    }

    @Test
    void deleteByExistingId() {
        Optional<Excursion> excursion = Optional.of(new Excursion(1, "Test"));

        when(mock.findById(1)).thenReturn(excursion);
        assertTrue(service.deleteById(1));
    }

    @Test
    void deleteByNotExistingId() {
        Optional<Excursion> excursion = Optional.empty();

        when(mock.findById(1)).thenReturn(excursion);

        assertThrows(NotDeletedException.class, () -> service.deleteById(1));
    }

    @Test
    void findByExistingId() {
        Optional<Excursion> excursion = Optional.of(new Excursion(1, "Test"));

        when(mock.findById(1)).thenReturn(excursion);

        assertEquals(excursion, service.findById(1));
    }

    @Test
    void findByNotExistingId() {
        Optional<Excursion> excursion = Optional.empty();

        when(mock.findById(1)).thenReturn(excursion);

        assertThrows(NotFoundException.class, () -> service.findById(1));
    }

    @Test
    void findByExistingName() {
        Optional<Excursion> excursion = Optional.of(new Excursion(1, "Test"));

        when(mock.findByName("Test")).thenReturn(excursion);

        assertEquals(excursion, service.findByName("Test"));
    }

    @Test
    void findByNotExistingName() {
        Optional<Excursion> excursion = Optional.empty();

        when(mock.findByName("Test")).thenReturn(excursion);

        assertThrows(NotFoundException.class, () -> service.findByName("Test"));
    }

    @Test
    void findAll() {
        List<Excursion> excursions = Arrays
                .asList(new Excursion(1, "Test1"), new Excursion(1, "Test1"));

        when(mock.findAll()).thenReturn(excursions);

        assertEquals(excursions, service.findAll());
    }

    @Test
    void findAllFails() {
        when(mock.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void updateExisting() {
        Optional<Excursion> excursion = Optional.of(new Excursion("Test"));

        when(mock.findByName("Test")).thenReturn(excursion);

        assertTrue(service.update(excursion.get()));
    }

    @Test
    void updateNotExisting() {
        Optional<Excursion> excursion = Optional.empty();

        when(mock.findByName("Test")).thenReturn(excursion);

        assertThrows(NotUpdatedException.class, () -> service.update(new Excursion("Test")));
    }

    @Test
    void findStatistic() {
        Map<Excursion, Integer> excursionCountMap = new HashMap<>();
        Date start = new Date(0);
        Date end = new Date(100500);
        ExcursionStatistic statistic = new ExcursionStatistic(excursionCountMap, start, end);

        when(mock.findStatistic(start, end)).thenReturn(statistic);

        ExcursionStatistic expected = service.findStatistic(start, end);

        assertEquals(excursionCountMap, expected.getExcursionCountMap());
        assertEquals(start, expected.getDateStart());
        assertEquals(end, expected.getDateEnd());
    }

    @Test
    void findStatisticFails() {
        when(mock.findStatistic(any(), any())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> service.findStatistic(new Date(0), new Date(0)));
    }

    @Test
    void findAvailable() {
        List<Excursion> available = Arrays
                .asList(new Excursion(1, "Test1"), new Excursion(2, "Test2"));

        when(mock.findAvailable(new Date(0), new Date(100500))).thenReturn(available);

        assertEquals(available, service.findAvailable(new Date(0), new Date(100500)));
    }

    @Test
    void findEmptyAvailable() {
        Date start = new Date(0);
        Date end = new Date(100500);
        when(mock.findAvailable(any(), any())).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> service.findAvailable(start, end));
    }
}