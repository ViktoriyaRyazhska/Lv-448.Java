package academy.softserve.museum.services;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.dto.EmployeeDto;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    boolean save(EmployeeDto dto);

    boolean deleteById(long id);

    Optional<Employee> findById(long id);

    List<Employee> findAll();

    Optional<Employee> findByFullName(String firstName, String lastName);

    boolean update(EmployeeDto dto);

    List<Employee> findByPosition(EmployeePosition position);

    EmployeeStatistic findStatistic(Date dateStart, Date dateEnd);

    boolean updateEmployeeAudience(Employee employee, Audience audience);

    List<Employee> findAvailable(Date dateStart, Date dateEnd);
}
