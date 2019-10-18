package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import academy.softserve.museum.services.EmployeeService;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;


    public EmployeeServiceImpl() {
        employeeDao = DaoFactory.employeeDao();
    }

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public boolean save(Employee objectToSave) {
        if (employeeDao.findByUsername(objectToSave.getLogin()) != null) {
            return false;
        } else {
            employeeDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (!employeeDao.findById(id).isPresent()) {
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
    public void update(Employee newObject) {
        employeeDao.update(newObject);
    }

    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        return employeeDao.loadForeignFields(employeeDao.findByPosition(position));
    }

    @Override
    public EmployeeStatistic findStatistic(Date dateStart, Date dateEnd) {
        return employeeDao.findStatistic(dateStart, dateEnd);
    }

    //TODO Change return parameter to Optional<Audience>
    @Override
    public Audience findAudienceByEmployee(Employee employee) {
        return employeeDao.findAudienceByEmployee(employee);
    }

    @Override
    public void updateEmployeeAudience(Employee employee, Audience audience) {
        employeeDao.updateEmployeeAudience(employee, audience);
    }

    @Override
    public Employee findByFullName(String firstName, String lastName) {
        return employeeDao.findByFullName(firstName, lastName);
    }
}
