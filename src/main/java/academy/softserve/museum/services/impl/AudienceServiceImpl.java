package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcAudienceDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.AudienceService;
import java.util.List;
import java.util.Optional;

public class AudienceServiceImpl implements AudienceService {

    private final AudienceDao jdbcAudienceDao;

    /**
     * Default constructor
     */
    public AudienceServiceImpl() {
        jdbcAudienceDao = DaoFactory.audienceDao();
    }

    /**
     * Constructor with 1 parameters
     *
     * @param jdbcAudienceDao
     */
    public AudienceServiceImpl(JdbcAudienceDao jdbcAudienceDao) {
        this.jdbcAudienceDao = jdbcAudienceDao;
    }

    /**
     * Method for saving object Audience in database
     *
     * @return true if the save was successful
     */
    @Override
    public boolean save(Audience audience) {
        if (jdbcAudienceDao.findByName(audience.getName()).isPresent()) {
            throw new NotSavedException(ErrorMessage.AUDIENCE_NOT_SAVED);
        } else {
            jdbcAudienceDao.save(audience);
            return true;
        }
    }

    /**
     * Method for deleting object Audience by id
     *
     * @return true if the delete was successful
     */
    @Override
    public boolean deleteById(long id) {
        if (jdbcAudienceDao.findById(id).isPresent()) {
            jdbcAudienceDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.AUDIENCE_NOT_DELETED);
        }
    }

    /**
     * Method, that returns object Audience wrapped in Optional by id
     *
     * @return Object Audience wrapped in Optional
     */
    @Override
    public Optional<Audience> findById(long id) {
        return Optional.of(jdbcAudienceDao.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method for finding Audience by audience name
     *
     * @param name of audience
     * @return Audience object wrapped in Optional
     */
    @Override
    public Optional<Audience> findByName(String name) {
        return Optional.of(jdbcAudienceDao.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method, that returns all objects of Audience
     *
     * @return list of Audience
     */
    @Override
    public List<Audience> findAll() {
        List<Audience> audienceList = jdbcAudienceDao.findAll();
        if (audienceList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return audienceList;
    }

    /**
     * Method, that updates given object Audience
     *
     * @return true if the update was successful
     */
    @Override
    public boolean update(Audience audience) {
        if (jdbcAudienceDao.findByName(audience.getName()).isPresent()) {
            jdbcAudienceDao.update(audience);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.AUDIENCE_NOT_UPDATED);
        }
    }

}
