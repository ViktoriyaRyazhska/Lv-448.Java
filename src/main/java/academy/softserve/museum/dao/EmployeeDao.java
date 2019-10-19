package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends Crud<Employee> {

    List<Employee> findByPosition(EmployeePosition position);

    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByFullName(String firstName, String lastName);

    EmployeeStatistic findStatistic(Date dateStart, Date dateEnd);

    List<Employee> findAvailable(Date dateStart, Date dateEnd);

    void updateAudience(Employee employee, Audience audience);

    Employee loadForeignFields(Employee employee);

    default List<Employee> loadForeignFields(List<Employee> employees) {
        employees.forEach(this::loadForeignFields);

        return employees;
    }
}
