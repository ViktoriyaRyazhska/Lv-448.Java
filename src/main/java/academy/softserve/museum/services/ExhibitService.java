package academy.softserve.museum.services;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExhibitService {

    boolean save(Exhibit objectToSave);

    boolean deleteById(long id);

    Optional<Exhibit> findById(long id);

    Optional<Exhibit> findByName(String name);

    List<Exhibit> findAll();

    boolean update(Exhibit newObject);

    List<Exhibit> findByAuthor(Author author);

    List<Exhibit> findByEmployee(Employee employee);

    List<Author> findAuthorsByExhibit(Exhibit exhibit);

    Audience findAudienceByExhibit(Exhibit exhibit);

    boolean updateExhibitAudience(Exhibit exhibit, Audience audience);

    boolean addExhibitAuthor(Exhibit exhibit, Author author);

    boolean deleteExhibitAuthor(Exhibit exhibit, Author author);

    Map<Audience, List<Exhibit>> findAllGroupedByAudience();

    ExhibitStatistic findStatistic();

}
