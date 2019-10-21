package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interface, that contains special methods for
 * getting / updating Employee objects from / in database.
 */
public interface EmployeeDao extends Crud<Employee> {

    /**
     * Method for finding Employees by position.
     *
     * @param position position of Employee.
     * @return list of Employees with given position.
     */
    List<Employee> findByPosition(EmployeePosition position);

    /**
     * Method for finding Employees by username (login).
     *
     * @param username username (login) of Employee.
     * @return Employee object wrapped in Optional or
     * Optional.empty() if there is no Employee with
     * such username (login).
     */
    Optional<Employee> findByUsername(String username);

    /**
     * Method for finding Employee by full name.
     *
     * @param firstName Employee's first name.
     * @param lastName  Employee's last name.
     * @return Employee object wrapped in Optional or
     * Optional.empty() if there is no Employee with
     * such full name.
     */
    Optional<Employee> findByFullName(String firstName, String lastName);

    /**
     * Method for finding statistic for all Employees.
     * Statistic contains work time in hours and excursion count
     * for each Employee.
     *
     * @param dateStart date and time for statistic calculating start.
     * @param dateEnd   date and time for statistic calculating end.
     * @return statistic.
     */
    EmployeeStatistic findStatistic(Date dateStart, Date dateEnd);

    /**
     * Method for finding available tour guides in some period.
     * Tour guide is available if he / she hasn't any excursion
     * in given period.
     *
     * @param dateStart period start date and time.
     * @param dateEnd   period end date and time.
     * @return list of available Employees.
     */
    List<Employee> findAvailable(Date dateStart, Date dateEnd);

    /**
     * Method for updating Audience that Employee is
     * responsible for.
     *
     * @param employee Employee for which you want to update Audience.
     * @param audience new Audience.
     */
    void updateAudience(Employee employee, Audience audience);

    /**
     * Method for loading and setting Employee's Audience
     *
     * @param employee Employee for which you want
     *                 to load and set Audience.
     * @return Given Employee with set Audience.
     */
    Employee loadForeignFields(Employee employee);

    /**
     * Method for loading and setting Employee's Audience
     *
     * @param employees list of Employee for which elements you want
     *                  to load and set Audience.
     * @return Given list of Employee with set Audiences.
     */
    default List<Employee> loadForeignFields(List<Employee> employees) {
        employees.forEach(this::loadForeignFields);

        return employees;
    }
}
