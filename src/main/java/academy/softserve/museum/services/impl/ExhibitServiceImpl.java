package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import academy.softserve.museum.entities.dto.ExhibitDto;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.ExhibitService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExhibitServiceImpl implements ExhibitService {

    private ExhibitDao exhibitDao;
    private AuthorDao authorDao;
    private AudienceDao audienceDao;

    /**
     * Default constructor
     */
    public ExhibitServiceImpl() {
        exhibitDao = DaoFactory.exhibitDao();
        authorDao = DaoFactory.authorDao();
        audienceDao = DaoFactory.audienceDao();
    }

    /**
     * Constructor with 3 parameters
     *
     * @param exhibitDao
     * @param authorDao
     * @param audienceDao
     */
    public ExhibitServiceImpl(ExhibitDao exhibitDao, AuthorDao authorDao,
            AudienceDao audienceDao) {
        this.exhibitDao = exhibitDao;
        this.authorDao = authorDao;
        this.audienceDao = audienceDao;
    }

    /**
     * Method for saving object Exhibit in database
     *
     * @return true if the save was successful
     */
    @Override
    public boolean save(ExhibitDto exhibitDto) {
        if (exhibitDao.findByName(exhibitDto.getName()).isPresent()) {
            throw new NotSavedException(ErrorMessage.EXHIBIT_NOT_SAVED);
        } else {
            Exhibit exhibit = new Exhibit(
                    exhibitDto.getType(),
                    exhibitDto.getMaterial(),
                    exhibitDto.getTechnique(),
                    exhibitDto.getName());

            exhibitDao.save(exhibit);
            Long exhibitId = exhibitDao.findByName(exhibit.getName()).get().getId();
            exhibit.setId(exhibitId);

            Audience audience = new Audience();
            audience.setId(exhibitDto.getAudienceId());

            exhibitDao.updateAudience(exhibit, audience);

            for (Long id : exhibitDto.getAuthorsId()) {
                Author author = new Author();
                author.setId(id);
                exhibitDao.addAuthor(exhibit, author);
            }
            return true;
        }
    }

    /**
     * Method for deleting object by id
     *
     * @return true if the delete was successful
     */
    @Override
    public boolean deleteById(long id) {
        if (exhibitDao.findById(id).isPresent()) {
            exhibitDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EXHIBIT_NOT_DELETED);
        }
    }

    /**
     * Method for deleting object Exhibit by id
     *
     * @return true if the delete was successful
     */
    @Override
    public Optional<Exhibit> findById(long id) {
        return Optional.of(Optional.of(exhibitDao.loadForeignFields(exhibitDao.findById(id)
                .orElse(null)))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method for finding Exhibit by name
     *
     * @param name of Exhibit
     * @return Exhibit object wrapped in Optional
     */
    @Override
    public Optional<Exhibit> findByName(String name) {
        return Optional.of(Optional.of(exhibitDao.loadForeignFields(exhibitDao.findByName(name)
                .orElse(null)))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method, that returns all objects of Exhibit
     *
     * @return list of Exhibit
     */
    @Override
    public List<Exhibit> findAll() {
        List<Exhibit> exhibitList = exhibitDao.loadForeignFields(exhibitDao.findAll());
        if (exhibitList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }

    /**
     * Method, that updates given object Exhibit
     *
     * @return true if the update was successful
     */
    @Override
    public boolean update(Exhibit exhibit) {
        if (exhibitDao.findByName(exhibit.getName()).isPresent()) {
            exhibitDao.update(exhibit);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EXHIBIT_NOT_UPDATED);
        }
    }

    /**
     * Method for finding Exhibits by Author
     *
     * @param author Author of Exhibit
     * @return list of Exhibits that have given Author
     */
    @Override
    public List<Exhibit> findByAuthor(Author author) {
        List<Exhibit> exhibitList = exhibitDao.loadForeignFields(exhibitDao.findByAuthor(author));
        if (exhibitList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }

    /**
     * Method for finding Exhibits by Author, that linked with Audience where Exhibit is
     *
     * @param employee Employee for which you want find Exhibits
     * @return list of Exhibits
     */
    @Override
    public List<Exhibit> findByEmployee(Employee employee) {
        List<Exhibit> exhibitList = exhibitDao
                .loadForeignFields(exhibitDao.findByEmployee(employee));
        if (exhibitList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }

    /**
     * Method for updating Audience where Exhibit located
     *
     * @param exhibit Exhibit for which you want to update Audience
     * @param audience new Audience for given Exhibit
     * @return true if the update was successful
     */
    @Override
    public boolean updateExhibitAudience(Exhibit exhibit, Audience audience) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (audienceDao.findById(audience.getId()).isPresent())) {
            exhibitDao.updateAudience(exhibit, audience);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EXHIBIT_AUDIENCE_NOT_UPDATED);
        }
    }

    /**
     * Method for adding Author for given Exhibit
     *
     * @param exhibit Exhibit for which you want to add Author
     * @param author new Author for Exhibit
     * @return true if the add was successful
     */
    @Override
    public boolean addExhibitAuthor(Exhibit exhibit, Author author) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (authorDao.findById(author.getId()).isPresent())) {
            throw new NotSavedException(ErrorMessage.EXHIBIT_AUTHOR_NOT_SAVED);
        } else {
            exhibitDao.addAuthor(exhibit, author);
            return true;
        }
    }

    /**
     * Method for deleting Author for given Exhibit
     *
     * @param exhibit Exhibit for which you want to delete Author
     * @param author Author to delete
     * @return true if the delete was successful
     */
    @Override
    public boolean deleteExhibitAuthor(Exhibit exhibit, Author author) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (authorDao.findById(author.getId()).isPresent())) {
            exhibitDao.deleteAuthor(exhibit, author);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EXHIBIT_NOT_DELETED);
        }
    }

    /**
     * Method for finding all Exhibits grouped by Audience
     *
     * @return Map with Audience as key and List of Exhibits in that Audience as a value
     */
    @Override
    public Map<Audience, List<Exhibit>> findAllGroupedByAudience() {
        Map<Audience, List<Exhibit>> map = exhibitDao.findAllGroupedByAudience();
        if (map.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return map;
    }

    /**
     * Method for finding statistic for all Exhibits. Statistic contains map with materials and
     * their count and map with techniques and their count.
     *
     * @return statistic.
     */
    @Override
    public ExhibitStatistic findStatistic() {
        try {
            return exhibitDao.findStatistic();
        } catch (Exception e) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
    }

    /**
     * Method for get list of types in enum ExhibitType
     *
     * @return types in enum ExhibitType
     */
    public List<ExhibitType> getTypes() {
        return Arrays.asList(ExhibitType.values());
    }

    /**
     * Method for finding Exhibits by Audience
     *
     * @param audience Audience for which you want to find Exhibits
     * @return list of Exhibits located in given Audience
     */
    @Override
    public List<Exhibit> findByAudience(Audience audience) {
        List<Exhibit> exhibitList = exhibitDao
                .loadForeignFields(exhibitDao.findByAudience(audience));
        if (exhibitList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }
}
