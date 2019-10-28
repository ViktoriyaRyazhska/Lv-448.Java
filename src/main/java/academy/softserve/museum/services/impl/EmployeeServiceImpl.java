package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.database.DaoFactory;
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

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;
    private AudienceDao audienceDao;

    /**
     * Default constructor
     */
    public EmployeeServiceImpl() {
        employeeDao = DaoFactory.employeeDao();
        audienceDao = DaoFactory.audienceDao();
    }

    /**
     * Constructor with 2 parameters
     *
     * @param employeeDao
     * @param audienceDao
     */
    public EmployeeServiceImpl(EmployeeDao employeeDao, AudienceDao audienceDao) {
        this.employeeDao = employeeDao;
        this.audienceDao = audienceDao;
    }

    /**
     * Method for saving object Employee in database
     *
     * @return true if the save was successful
     */
    @Override
    public boolean save(EmployeeDto employeeDto) {
        if (employeeDao.findByUsername(employeeDto.getUsername()).isPresent()) {
            throw new NotSavedException(ErrorMessage.EMPLOYEE_NOT_SAVED);
        } else {
            Employee employee = new Employee(
                    employeeDto.getFirstName(),
                    employeeDto.getLastName(),
                    employeeDto.getPosition(),
                    employeeDto.getUsername(),
                    employeeDto.getPassword());

            employeeDao.save(employee);
            Long id = employeeDao.findByFullName(employee.getFirstName(), employee.getLastName())
                    .get().getId();
            employee.setId(id);

            Audience audience = new Audience();
            audience.setId(employeeDto.getAudienceId());

            employeeDao.updateAudience(employee, audience);
            return true;
        }
    }

    /**
     * Method for deleting object Employee by id
     *
     * @return true if the delete was successful
     */
    @Override
    public boolean deleteById(long id) {
        if (employeeDao.findById(id).isPresent()) {
            employeeDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EMPLOYEE_NOT_DELETED);
        }
    }

    /**
     * Method for deleting object Employee by id
     *
     * @return true if the delete was successful
     */
    @Override
    public Optional<Employee> findById(long id) {
        return Optional.of(employeeDao.loadForeignFields(employeeDao.findById(id).
                orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND))));
    }

    /**
     * Method, that returns all objects of Employee
     *
     * @return list of Employee
     */
    @Override
    public List<Employee> findAll() {
        List<Employee> employeeList = employeeDao.loadForeignFields(employeeDao.findAll());
        if (employeeList.isEmpty()) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return employeeList;
    }

    /**
     * Method, that updates given object Employee
     *
     * @return true if the update was successful
     */
    @Override
    public boolean update(EmployeeDto dto) {
        if (employeeDao.findById(dto.getId()).isPresent()) {
            Employee employee = new Employee(
                    dto.getId(),
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getPosition(),
                    dto.getUsername(),
                    dto.getPassword()
            );
            employeeDao.update(employee);
            Audience audience = new Audience();
            audience.setId(dto.getAudienceId());

            employeeDao.updateAudience(employee, audience);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EMPLOYEE_NOT_UPDATED);
        }
    }

    /**
     * Method for finding Employees by position
     *
     * @param position of Employee
     * @return list of Employees
     */
    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        List<Employee> employeeList = employeeDao
                .loadForeignFields(employeeDao.findByPosition(position));
        if (employeeList.isEmpty()) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return employeeList;
    }

    /**
     * Method for finding statistic for all Employees. Statistic contains work time in hours and
     * excursion count for each Employee
     *
     * @return statistic
     */
    @Override
    public EmployeeStatistic findStatistic(Date dateStart, Date dateEnd) {
        try {
            return employeeDao.findStatistic(dateStart, dateEnd);
        } catch (Exception e) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
    }

    /**
     * Method for updating Audience that Employee is responsible for
     *
     * @param employee Employee for which you want to update Audience
     * @param audience new Audience
     * @return true if the update was successful
     */
    @Override
    public boolean updateEmployeeAudience(Employee employee, Audience audience) {
        if ((employeeDao.findByFullName(employee.getFirstName(), employee.getLastName())
                .isPresent())
                && (audienceDao.findByName(audience.getName()).isPresent())) {
            employeeDao.updateAudience(employee, audience);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EMPLOYEE_AUDIENCE_NOT_UPDATED);
        }
    }

    /**
     * Method for finding available tour guides in some period. Tour guide is available if he / she
     * hasn't any excursion in given period
     *
     * @return list of available Employees
     */
    @Override
    public List<Employee> findAvailable(Date dateStart, Date dateEnd) {
        List<Employee> employeeList = employeeDao.findAvailable(dateStart, dateEnd);
        if (employeeList.isEmpty()) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return employeeList;
    }

    /**
     * Method for finding Employee by first and last names
     *
     * @return Employee object wrapped in Optional
     */
    @Override
    public Optional<Employee> findByFullName(String firstName, String lastName) {
        return Optional.of(employeeDao.findByFullName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }
}
