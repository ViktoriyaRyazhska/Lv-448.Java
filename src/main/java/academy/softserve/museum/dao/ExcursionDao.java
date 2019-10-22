package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interface, that contains special methods for
 * getting / updating Excursion objects from database.
 */
public interface ExcursionDao extends Crud<Excursion> {

    /**
     * Method for finding statistic for all Excursions.
     * Statistic contains conducted excursions count
     * for each possible excursion.
     *
     * @param dateStart date and time for statistic calculating start.
     * @param dateEnd   date and time for statistic calculating end.
     * @return statistic.
     */
    ExcursionStatistic findStatistic(Date dateStart, Date dateEnd);

    /**
     * Method for finding Excursion by name.
     *
     * @param name name of excursion.
     * @return Excursion object wrapped in Optional or
     * Optional.empty() if there is no Excursion with
     * such name.
     */
    Optional<Excursion> findByName(String name);

    /**
     * Method for finding available Excursions in some period.
     * Excursion is available if it starts in given period.
     *
     * @param dateStart period start date and time.
     * @param dateEnd   period end date and time.
     * @return list of available Excursions.
     */
    List<Excursion> findAvailable(Date dateStart, Date dateEnd);
}
