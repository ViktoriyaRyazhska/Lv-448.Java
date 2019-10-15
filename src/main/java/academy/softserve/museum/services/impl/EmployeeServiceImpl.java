package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.EmployeeDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcEmployeeDao;
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

    private EmployeeDao jdbcEmployeeDao = DaoFactory.employeeDao();

    @Override
    public boolean save(Employee objectToSave) {
        if(jdbcEmployeeDao.findByUsername(objectToSave.getLogin()) != null) {
            return false;
        } else {
            jdbcEmployeeDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public void deleteById(long id) {
        jdbcEmployeeDao.deleteById(id);
    }

    @Override
    public Optional<Employee> findById(long id) {
        return jdbcEmployeeDao.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return jdbcEmployeeDao.findAll();
    }

    @Override
    public void update(Employee newObject) {
        jdbcEmployeeDao.update(newObject);
    }

    @Override
    public List<Employee> findByPosition(EmployeePosition position) {
        return jdbcEmployeeDao.findByPosition(position);
    }

    @Override
    public EmployeeStatistic findStatistic(Date dateStart, Date dateEnd) {
        return jdbcEmployeeDao.findStatistic(dateStart, dateEnd);
    }

    @Override
    public Audience findAudienceByEmployee(Employee employee) {
        return jdbcEmployeeDao.findAudienceByEmployee(employee);
    }

    @Override
    public void updateEmployeeAudience(Employee employee, Audience audience) {
        jdbcEmployeeDao.updateEmployeeAudience(employee, audience);
    }
}
