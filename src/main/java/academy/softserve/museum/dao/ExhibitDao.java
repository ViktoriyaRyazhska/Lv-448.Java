package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExhibitDao extends Crud<Exhibit> {

    Optional<Exhibit> findByName(String name);

    List<Exhibit> findByAuthor(Author author);

    List<Exhibit> findByEmployee(Employee employee);

    List<Exhibit> findByAudience(Audience audience);

    void updateAudience(Exhibit exhibit, Audience audience);

    void addAuthor(Exhibit exhibit, Author author);

    void deleteAuthor(Exhibit exhibit, Author author);

    Map<Audience, List<Exhibit>> findAllGroupedByAudience();

    ExhibitStatistic findStatistic();

    Exhibit loadForeignFields(Exhibit exhibit);

    default List<Exhibit> loadForeignFields(List<Exhibit> exhibits) {
        exhibits.forEach(this::loadForeignFields);

        return exhibits;
    }
}
