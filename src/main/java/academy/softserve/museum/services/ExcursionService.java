package academy.softserve.museum.services;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ExcursionService {

    /**
     * Method for saving object Excursion in database
     *
     * @return true if the save was successful
     */
    boolean save(Excursion objectToSave);

    /**
     * Method for deleting object by id
     *
     * @return true if the delete was successful
     */
    boolean deleteById(long id);

    /**
     * Method for deleting object Excursion by id
     *
     * @return true if the delete was successful
     */
    Optional<Excursion> findById(long id);

    /**
     * Method for finding Excursion by name
     *
     * @param name of excursion
     * @return Excursion object wrapped in Optional
     */
    Optional<Excursion> findByName(String name);

    /**
     * Method, that returns all objects of Excursion
     *
     * @return list of Excursion
     */
    List<Excursion> findAll();

    /**
     * Method, that updates given object Excursion
     *
     * @return true if the update was successful
     */
    boolean update(Excursion excursion);

    /**
     * Method for finding statistic for all Employees. Statistic contains work time in hours and
     * excursion count for each Employee
     *
     * @return statistic
     */
    ExcursionStatistic findStatistic(Date dateStart, Date dateEnd);

    /**
     * Method for finding available Excursions in some period. Excursion is available if it starts
     * in given period.
     *
     * @param dateStart period start date and time.
     * @param dateEnd period end date and time.
     * @return list of available Excursions.
     */
    List<Excursion> findAvailable(Date dateStart, Date dateEnd);
}
