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

    List<Author> findAuthorsByExhibit(Exhibit exhibit);

    Audience findAudienceByExhibit(Exhibit exhibit);

    void updateExhibitAudience(Exhibit exhibit, Audience audience);

    void addExhibitAuthor(Exhibit exhibit, Author author);

    void deleteExhibitAuthor(Exhibit exhibit, Author author);

    Map<Audience, List<Exhibit>> findAllGroupedByAudience();

    ExhibitStatistic findStatistic();

    default Exhibit loadForeignFields(Exhibit exhibit) {
        exhibit.setAudience(findAudienceByExhibit(exhibit));
        exhibit.setAuthors(findAuthorsByExhibit(exhibit));

        return exhibit;
    }

    default List<Exhibit> loadForeignFields(List<Exhibit> exhibits) {
        exhibits.forEach(e -> {
            e.setAudience(findAudienceByExhibit(e));
            e.setAuthors(findAuthorsByExhibit(e));
        });

        return exhibits;
    }
}
