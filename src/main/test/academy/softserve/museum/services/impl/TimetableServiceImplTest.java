package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.TimetableDao;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.entities.dto.TimetableDto;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.TimetableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

class TimetableServiceImplTest {

    @Mock
    TimetableDao timetableDaoMock;

    @InjectMocks
    TimetableService timetableService = new TimetableServiceImpl();

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void saveSuccessTest() {
        TimetableDto timetableDto = new TimetableDto(new Date(1L), new Date(1L), 1L, 2L);
        timetableService.save(timetableDto);
    }

    // Delete tests
    @Test
    void deleteByIdSuccessTest() {
        Optional<Timetable> timetable = Optional.of(new Timetable());
        when(timetableDaoMock.findById(anyLong())).thenReturn(timetable);
        assertTrue(timetableService.deleteById(anyLong()));
    }

    @Test
    void deleteByIdFailureTest() {
        Optional<Timetable> timetable = Optional.empty();
        when(timetableDaoMock.findById(anyLong())).thenReturn(timetable);
        assertThrows(NotDeletedException.class, () -> timetableService.deleteById(anyLong()));
    }

    // Find by id tests
    @Test
    void findByIdSuccessTest() {
        Optional<Timetable> timetable = Optional.of(new Timetable());
        when(timetableDaoMock.findById(anyLong())).thenReturn(timetable);
        assertEquals(timetable, timetableService.findById(timetable.get().getId()));
    }

    @Test
    void findByIdFailureTest() {
        Optional<Timetable> timetable = Optional.empty();
        when(timetableDaoMock.findById(anyLong())).thenReturn(timetable);
        assertThrows(NotFoundException.class, () -> timetableService.findById(new Timetable().getId()));
    }

    // Find all tests
    @Test
    void findAllSuccessTest() {
        List<Timetable> timetables = Arrays.asList(new Timetable(), new Timetable());
        when(timetableDaoMock.findAll()).thenReturn(timetables);
        assertEquals(timetables, timetableService.findAll());
    }

    @Test
    void findAllFailureTest() {
        List<Timetable> timetables = Collections.emptyList();
        when(timetableDaoMock.findAll()).thenReturn(timetables);
        assertThrows(NotFoundException.class, () -> timetableService.findAll());
    }

    // Update tests
    @Test
    void updateSuccessTest() {
        Optional<Timetable> timetable = Optional.of(new Timetable());
        when(timetableDaoMock.findById(anyLong())).thenReturn(timetable);
        assertTrue(timetableService.update(timetable.get()));
    }

    @Test
    void updateFailureTest() {
        Optional<Timetable> timetable =
                Optional.of(new Timetable(1L, new Employee(), new Excursion(), new Date(1L), new Date(1L)));
        when(timetableDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotUpdatedException.class, () -> timetableService.update(timetable.get()));
    }
}