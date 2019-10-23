package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.dto.EmployeeDto;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import academy.softserve.museum.services.EmployeeService;
import java.sql.Date;
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
            return false;
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
            return false;
        }
    }

    @Override
    public Optional<Employee> findById(long id) {
        Employee employee = employeeDao.findById(id).orElse(null);
        if (employee != null) {
            return Optional.of(employeeDao.loadForeignFields(employee));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.loadForeignFields(employeeDao.findAll());
    }

    @Override
    public boolean update(Employee newObject) {
        if (employeeDao.findByFullName(newObject.getFirstName(), newObject.getLastName())
                .isPresent()) {
            employeeDao.update(newObject);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        return employeeDao.loadForeignFields(employeeDao.findByPosition(position));
    }

    @Override
    public EmployeeStatistic findStatistic(Date dateStart, Date dateEnd) {
        return employeeDao.findStatistic(dateStart, dateEnd);
    }

    @Override
    public boolean updateEmployeeAudience(Employee employee, Audience audience) {
        if ((employeeDao.findByFullName(employee.getFirstName(), employee.getLastName())
                .isPresent())
                && (audienceDao.findByName(audience.getName()).isPresent())) {
            employeeDao.updateAudience(employee, audience);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Employee> findAvailable(Date dateStart, Date dateEnd) {
        return employeeDao.findAvailable(dateStart, dateEnd);
    }

    @Override
    public Employee findByFullName(String firstName, String lastName) {
        return employeeDao.findByFullName(firstName, lastName).orElse(null);
    }
}
