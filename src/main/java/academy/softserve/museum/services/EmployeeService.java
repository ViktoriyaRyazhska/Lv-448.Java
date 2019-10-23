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

    /**
     * Method for saving object Employee in database
     *
     * @return true if the save was successful
     */
    boolean save(EmployeeDto employeeDto);

    /**
     * Method for deleting object Employee by id
     *
     * @return true if the delete was successful
     */
    boolean deleteById(long id);

    /**
     * Method for deleting object Employee by id
     *
     * @return true if the delete was successful
     */
    Optional<Employee> findById(long id);

    /**
     * Method, that returns all objects of Employee
     *
     * @return list of Employee
     */
    List<Employee> findAll();

    /**
     * Method for finding Employee by first and last names
     *
     * @return Employee object wrapped in Optional
     */
    Optional<Employee> findByFullName(String firstName, String lastName);

    /**
     * Method, that updates given object Employee
     *
     * @return true if the update was successful
     */
    boolean update(EmployeeDto dto);

    /**
     * Method for finding Employees by position
     *
     * @param position of Employee
     * @return list of Employees
     */
    List<Employee> findByPosition(EmployeePosition position);

    /**
     * Method for finding statistic for all Employees. Statistic contains work time in hours and
     * excursion count for each Employee
     *
     * @return statistic
     */
    EmployeeStatistic findStatistic(Date dateStart, Date dateEnd);

    /**
     * Method for updating Audience that Employee is responsible for
     *
     * @param employee Employee for which you want to update Audience
     * @param audience new Audience
     * @return true if the update was successful
     */
    boolean updateEmployeeAudience(Employee employee, Audience audience);

    /**
     * Method for finding available tour guides in some period. Tour guide is available if he / she
     * hasn't any excursion in given period
     *
     * @return list of available Employees
     */
    List<Employee> findAvailable(Date dateStart, Date dateEnd);
}
