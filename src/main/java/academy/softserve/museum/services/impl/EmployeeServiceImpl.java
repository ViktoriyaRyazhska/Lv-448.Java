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
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final AudienceDao audienceDao;

    public EmployeeServiceImpl() {
        employeeDao = DaoFactory.employeeDao();
        audienceDao = DaoFactory.audienceDao();
    }

    public EmployeeServiceImpl(EmployeeDao employeeDao, AudienceDao audienceDao) {
        this.employeeDao = employeeDao;
        this.audienceDao = audienceDao;
    }

    @Override
    public boolean save(EmployeeDto dto) {
        if (employeeDao.findByUsername(dto.getUsername()).isPresent()) {
            throw new NotSavedException(ErrorMessage.EMPLOYEE_NOT_SAVED);
        } else {
            Employee employee = new Employee(
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getPosition(),
                    dto.getUsername(),
                    dto.getPassword());

            employeeDao.save(employee);
            Long id = employeeDao.findByFullName(employee.getFirstName(), employee.getLastName()).get().getId();
            employee.setId(id);

            Audience audience = new Audience();
            audience.setId(dto.getAudienceId());

            employeeDao.updateAudience(employee, audience);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (employeeDao.findById(id).isPresent()) {
            employeeDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EMPLOYEE_NOT_DELETED);
        }
    }

    @Override
    public Optional<Employee> findById(long id) {
        Employee employee = employeeDao.findById(id).orElse(null);
            return Optional.of(Optional.of(employeeDao.loadForeignFields(employee))
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employeeList = employeeDao.loadForeignFields(employeeDao.findAll());
        if(employeeList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return employeeList;
    }

    @Override
    public boolean update(Employee newObject) {
        if (employeeDao.findByFullName(newObject.getFirstName(), newObject.getLastName())
                .isPresent()) {
            employeeDao.update(newObject);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EMPLOYEE_NOT_UPDATED);
        }
    }

    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        List<Employee> employeeList = employeeDao.loadForeignFields(employeeDao.findByPosition(position));
        if(employeeList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return employeeList;
    }

    @Override
    public EmployeeStatistic findStatistic(Date dateStart, Date dateEnd) {
        try {
            return employeeDao.findStatistic(dateStart, dateEnd);
        } catch (Exception e){
        throw new  NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
    }

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

    @Override
    public List<Employee> findAvailable(Date dateStart, Date dateEnd) {
        List<Employee> employeeList = employeeDao.findAvailable(dateStart, dateEnd);
        if(employeeList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return employeeList;
    }

    @Override
    public Optional<Employee> findByFullName(String firstName, String lastName) {
        return Optional.of(employeeDao.findByFullName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }
}
