package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface, that contains special methods for
 * getting / updating Exhibit objects from database.
 */
public interface ExhibitDao extends Crud<Exhibit> {

    /**
     * Method for finding Exhibit by name.
     *
     * @param name name of Exhibit.
     * @return Exhibit object wrapped in Optional or
     * Optional.empty() if there is no Exhibit with
     * such name.
     */
    Optional<Exhibit> findByName(String name);

    /**
     * Method for finding Exhibits by Author.
     *
     * @param author Author of Exhibit.
     * @return list of Exhibits that have
     * given Author.
     */
    List<Exhibit> findByAuthor(Author author);

    /**
     * Method for finding Exhibits by Author,
     * that linked with Audience where Exhibit is.
     *
     * @param employee Employee for which you want
     *                 find Exhibits.
     * @return list of Exhibits.
     */
    List<Exhibit> findByEmployee(Employee employee);

    /**
     * Method for finding Exhibits by Audience.
     *
     * @param audience Audience for which you want
     *                 to find Exhibits.
     * @return list of Exhibits located in given Audience.
     */
    List<Exhibit> findByAudience(Audience audience);

    /**
     * Method for updating Audience where Exhibit located.
     *
     * @param exhibit  Exhibit for which you want to update
     *                 Audience.
     * @param audience new Audience for given Exhibit.
     */
    void updateAudience(Exhibit exhibit, Audience audience);

    /**
     * Method for adding Author for given Exhibit.
     *
     * @param exhibit Exhibit for which you want to add
     *                Author.
     * @param author  new Author for Exhibit.
     */
    void addAuthor(Exhibit exhibit, Author author);

    /**
     * Method for deleting Author for given Exhibit.
     *
     * @param exhibit Exhibit for which you want to
     *                delete Author.
     * @param author  Author to delete.
     */
    void deleteAuthor(Exhibit exhibit, Author author);

    /**
     * Method for finding all Exhibits grouped by Audience.
     *
     * @return Map with Audience as key and List of Exhibits in
     * that Audience as a value.
     */
    Map<Audience, List<Exhibit>> findAllGroupedByAudience();

    /**
     * Method for finding statistic for all Exhibits.
     * Statistic contains map with materials and their count
     * and map with techniques and their count.
     *
     * @return statistic.
     */
    ExhibitStatistic findStatistic();

    /**
     * Method for loading and setting Exhibit's Audience and Authors.
     *
     * @param exhibit Exhibit for which you want to load and set
     *                Audience and Authors.
     * @return Given Exhibit with set Audience and Authors.
     */
    Exhibit loadForeignFields(Exhibit exhibit);

    /**
     * Method for loading and setting Exhibit's Audience and Authors.
     *
     * @param exhibits list of Exhibit for which elements you want to load and set
     *                 Audience and Authors.
     * @return Given list of Exhibits with set Audience and Authors.
     */
    default List<Exhibit> loadForeignFields(List<Exhibit> exhibits) {
        exhibits.forEach(this::loadForeignFields);

        return exhibits;
    }
}
