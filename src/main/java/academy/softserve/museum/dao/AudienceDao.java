package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;

import java.util.Optional;

/**
 * Interface, that contains special methods for
 * getting / updating Audience objects from database.
 */
public interface AudienceDao extends Crud<Audience> {

    /**
     * Method for finding Audience by audience name
     *
     * @param name audience name
     * @return Audience object wrapped in Optional or
     * Optional.empty() if there is no Audience with
     * such name.
     */
    Optional<Audience> findByName(String name);

    /**
     * Method for finding Audience by employee.
     *
     * @param employee employee for which you want
     *                 to find Audience.
     * @return Audience wrapped in Optional linked with employee
     * or Optional.empty() if there is no such audience.
     */
    Optional<Audience> findByEmployee(Employee employee);

    /**
     * Method for finding Audience by exhibit.
     *
     * @param exhibit exhibit for which you want
     *                to find audience.
     * @return Audience wrapped in Optional linked with exhibit
     * or Optional.empty() if there is no such audience.
     */
    Optional<Audience> findByExhibit(Exhibit exhibit);
}
