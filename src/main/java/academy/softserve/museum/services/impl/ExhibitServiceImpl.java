package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.services.ExhibitService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExhibitServiceImpl implements ExhibitService {

    private ExhibitDao exhibitDao;
    private AuthorDao authorDao;
    private AudienceDao audienceDao;

    @Override
    public boolean save(Exhibit objectToSave) {
        if (exhibitDao.findByName(objectToSave.getName()).isPresent()) {
            return false;
        } else {
            exhibitDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (exhibitDao.findById(id).isPresent()) {
            return false;
        } else {
            exhibitDao.deleteById(id);
            return true;
        }
    }

    @Override
    public Optional<Exhibit> findById(long id) {
        return exhibitDao.findById(id);
    }

    @Override
    public Optional<Exhibit> findByName(String name) {
        return exhibitDao.findByName(name);
    }

    @Override
    public List<Exhibit> findAll() {
        return exhibitDao.findAll();
    }

    @Override
    public boolean update(Exhibit newObject) {
        if (exhibitDao.findByName(newObject.getName()).isPresent()) {
            exhibitDao.update(newObject);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Exhibit> findByAuthor(Author author) {
        return exhibitDao.findByAuthor(author);
    }

    @Override
    public List<Exhibit> findByEmployee(Employee employee) {
        return exhibitDao.findByEmployee(employee);
    }

    @Override
    public List<Author> findAuthorsByExhibit(Exhibit exhibit) {
        return exhibitDao.findAuthorsByExhibit(exhibit);
    }

    @Override
    public Audience findAudienceByExhibit(Exhibit exhibit) {
        return exhibitDao.findAudienceByExhibit(exhibit);
    }

    @Override
    public boolean updateExhibitAudience(Exhibit exhibit, Audience audience) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (audienceDao.findById(audience.getId()).isPresent())) {
            exhibitDao.updateExhibitAudience(exhibit, audience);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addExhibitAuthor(Exhibit exhibit, Author author) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (authorDao.findById(author.getId()).isPresent())) {
            return false;
        } else {
            exhibitDao.addExhibitAuthor(exhibit, author);
            return true;
        }
    }

    @Override
    public boolean deleteExhibitAuthor(Exhibit exhibit, Author author) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (authorDao.findById(author.getId()).isPresent())) {
            exhibitDao.deleteExhibitAuthor(exhibit, author);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<Audience, List<Exhibit>> findAllGroupedByAudience() {
        return exhibitDao.findAllGroupedByAudience();
    }

    @Override
    public ExhibitStatistic findStatistic() {
        return exhibitDao.findStatistic();
    }
}
