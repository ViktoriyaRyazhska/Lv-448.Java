package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;

import java.sql.Date;
import java.util.List;

public interface EmployeeDao extends Crud<Employee> {

    List<Employee> findByPosition(EmployeePosition position);

    Employee findByUsername(String username);

    Employee findByFullName(String firstName, String lastName);

    EmployeeStatistic findStatistic(Date dateStart, Date dateEnd);

    Audience findAudienceByEmployee(Employee employee);

    void updateEmployeeAudience(Employee employee, Audience audience);

    default Employee loadForeignFields(Employee employee) {
        employee.setAudience(findAudienceByEmployee(employee));

        return employee;
    }

    default List<Employee> loadForeignFields(List<Employee> employees) {
        employees.forEach(e -> e.setAudience(findAudienceByEmployee(e)));

        return employees;
    }
}
