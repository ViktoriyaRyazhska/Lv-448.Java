package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.AudienceDao;
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

    private AudienceDao audienceDao;

    /**
     * Default constructor
     */
    public AudienceServiceImpl() {
        audienceDao = DaoFactory.audienceDao();
    }

    /**
     * Constructor with 1 parameters
     *
     * @param audienceDao
     */
    public AudienceServiceImpl(AudienceDao audienceDao) {
        this.audienceDao = audienceDao;
    }

    /**
     * Method for saving object Audience in database
     *
     * @return true if the save was successful
     */
    @Override
    public boolean save(Audience audience) {
        if (audienceDao.findByName(audience.getName()).isPresent()) {
            throw new NotSavedException(ErrorMessage.AUDIENCE_NOT_SAVED);
        } else {
            audienceDao.save(audience);
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
        if (audienceDao.findById(id).isPresent()) {
            audienceDao.deleteById(id);
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
        return Optional.of(audienceDao.findById(id)
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
        return Optional.of(audienceDao.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method, that returns all objects of Audience
     *
     * @return list of Audience
     */
    @Override
    public List<Audience> findAll() {
        List<Audience> audienceList = audienceDao.findAll();
        if (audienceList.isEmpty()) {
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
        if (audienceDao.findByName(audience.getName()).isPresent()) {
            audienceDao.update(audience);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.AUDIENCE_NOT_UPDATED);
        }
    }

}
