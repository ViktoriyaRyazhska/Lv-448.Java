package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.dto.EmployeeDto;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class EmployeeServiceImplTest {
    @Mock
    private EmployeeDao employeeDaoMock;
    @Mock
    private AudienceDao audienceDaoMock;
    @InjectMocks
    private EmployeeService service = new EmployeeServiceImpl();
    private Employee testEmployee;
    private EmployeeDto testEmployeeDto;

    @BeforeEach
    void init() {
        initMocks(this);

        testEmployeeDto = new EmployeeDto(1L, "Test", "Test", "Test", "Test",
                EmployeePosition.MANAGER, 1L);
        testEmployee = new Employee(1L, "Test", "Test",
                EmployeePosition.MANAGER, "Test", "Test");
    }

    @Test
    void saveExisting() {

        when(employeeDaoMock.findByUsername("Test")).thenReturn(Optional.of(testEmployee));

        assertThrows(NotSavedException.class, () -> service.save(testEmployeeDto));
    }

    @Test
    void saveNotExisting() {
        Optional<Employee> emptyEmployee = Optional.empty();

        when(employeeDaoMock.findByUsername("Test")).thenReturn(emptyEmployee);
        when(employeeDaoMock.findByFullName("Test", "Test")).thenReturn(Optional.of(testEmployee));

        assertTrue(service.save(testEmployeeDto));
    }

    @Test
    void deleteByExistingId() {
        long id = 1;

        when(employeeDaoMock.findById(id)).thenReturn(Optional.of(testEmployee));

        assertTrue(service.deleteById(id));
    }

    @Test
    void deleteByNotExistingId() {
        long id = 1;
        Optional<Employee> employee = Optional.empty();

        when(employeeDaoMock.findById(id)).thenReturn(employee);

        assertThrows(NotDeletedException.class, () -> service.deleteById(id));
    }

    @Test
    void findByExistingId() {
        long id = 1;

        when(employeeDaoMock.findById(id)).thenReturn(Optional.of(testEmployee));
        when(employeeDaoMock.loadForeignFields(testEmployee)).thenReturn(testEmployee);

        assertEquals(Optional.of(testEmployee), service.findById(id));
    }

    @Test
    void findByNotExistingId() {
        long id = 1;

        when(employeeDaoMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(id));
    }

    @Test
    void findAll() {
        List<Employee> employees = Arrays.asList(testEmployee, testEmployee);

        when(employeeDaoMock.findAll()).thenReturn(employees);
        when(employeeDaoMock.loadForeignFields(employees)).thenReturn(employees);

        assertEquals(employees, service.findAll());
    }

    @Test
    void findAllFails() {
        List<Employee> employees = Collections.emptyList();

        when(employeeDaoMock.findAll()).thenReturn(employees);
        when(employeeDaoMock.loadForeignFields(employees)).thenReturn(employees);

        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void updateExisting() {
        long id = 1;

        when(employeeDaoMock.findById(id)).thenReturn(Optional.of(testEmployee));

        assertTrue(service.update(testEmployeeDto));
    }

    @Test
    void updateNotExisting() {
        long id = 1;

        when(employeeDaoMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotUpdatedException.class, () -> service.update(testEmployeeDto));
    }

    @Test
    void findByPosition() {
        List<Employee> employees = Arrays.asList(testEmployee, testEmployee);

        when(employeeDaoMock.findByPosition(EmployeePosition.MANAGER)).thenReturn(employees);
        when(employeeDaoMock.loadForeignFields(employees)).thenReturn(employees);

        assertEquals(employees, service.findByPosition(EmployeePosition.MANAGER));
    }

    @Test
    void findByPositionFails() {
        List<Employee> employees = Collections.emptyList();

        when(employeeDaoMock.findByPosition(EmployeePosition.MANAGER)).thenReturn(employees);
        when(employeeDaoMock.loadForeignFields(employees)).thenReturn(employees);

        assertThrows(NotFoundException.class, () -> service.findByPosition(EmployeePosition.MANAGER));
    }

    @Test
    void findStatistic() {
        Map<Employee, Integer> workTimeMap = new HashMap<>();
        Map<Employee, Integer> excursionCount = new HashMap<>();
        EmployeeStatistic statistic = new EmployeeStatistic();
        Date statisticStart = new Date(0);
        Date statisticEnd = new Date(100);

        workTimeMap.put(testEmployee, 10);
        excursionCount.put(testEmployee, 12);
        statistic.setWorkTimeMap(workTimeMap);
        statistic.setExcursionCount(excursionCount);

        statistic.setDateStart(statisticStart);
        statistic.setDateEnd(statisticEnd);

        when(employeeDaoMock.findStatistic(statisticStart, statisticEnd)).thenReturn(statistic);

        EmployeeStatistic expected = service.findStatistic(statisticStart, statisticEnd);

        assertEquals(workTimeMap, expected.getWorkTimeMap());
        assertEquals(excursionCount, expected.getExcursionCount());
        assertEquals(statisticStart, expected.getDateStart());
        assertEquals(statisticEnd, expected.getDateEnd());
    }

    @Test
    void findStatisticFails() {
        Date start = new Date(0);
        Date end = new Date(100);

        when(employeeDaoMock.findStatistic(start, end)).thenThrow(RuntimeException.class);

        assertThrows(NotFoundException.class, () -> service.findStatistic(start, end));
    }

    @Test
    void updateExistingEmployeeAudience() {
        Audience audience = new Audience(1, "Test");

        when(employeeDaoMock.findByFullName(testEmployee.getFirstName(), testEmployee.getLastName())).
                thenReturn(Optional.of(testEmployee));
        when(audienceDaoMock.findByName(audience.getName())).thenReturn(Optional.of(audience));

        assertTrue(service.updateEmployeeAudience(testEmployee, audience));
    }

    @Test
    void updateNotExistingEmployeeAudience() {
        Audience audience = new Audience(1, "Test");

        when(employeeDaoMock.findByFullName(testEmployee.getFirstName(), testEmployee.getLastName())).
                thenReturn(Optional.empty());
        when(audienceDaoMock.findByName(audience.getName())).thenReturn(Optional.of(audience));

        assertThrows(NotUpdatedException.class, () -> service.updateEmployeeAudience(testEmployee, audience));
    }

    @Test
    void findAvailable() {
        Date start = new Date(0);
        Date end = new Date(100);
        List<Employee> available = Arrays.asList(testEmployee, testEmployee);

        when(employeeDaoMock.findAvailable(start, end)).thenReturn(available);

        assertEquals(available, service.findAvailable(start, end));
    }

    @Test
    void findAvailableFails(){
        Date start = new Date(0);
        Date end = new Date(100);
        List<Employee> available = Collections.emptyList();

        when(employeeDaoMock.findAvailable(start, end)).thenReturn(available);

        assertThrows(NotFoundException.class, () -> service.findAvailable(start, end));
    }

    @Test
    void findByExistingFullName() {
        when(employeeDaoMock.findByFullName(testEmployee.getFirstName(), testEmployee.getLastName())).
                thenReturn(Optional.of(testEmployee));

        assertEquals(Optional.of(testEmployee), service.findByFullName(testEmployee.getFirstName(), testEmployee.getLastName()));
    }

    @Test
    void findByNotExistingFullName() {
        when(employeeDaoMock.findByFullName(testEmployee.getFirstName(), testEmployee.getLastName())).
                thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findByFullName(testEmployee.getFirstName(), testEmployee.getLastName()));
    }
}