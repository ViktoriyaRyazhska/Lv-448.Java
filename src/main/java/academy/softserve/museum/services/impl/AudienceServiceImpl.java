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

    public AudienceServiceImpl() {
        jdbcAudienceDao = DaoFactory.audienceDao();
    }

    public AudienceServiceImpl(JdbcAudienceDao jdbcAudienceDao) {
        this.jdbcAudienceDao = jdbcAudienceDao;
    }

    @Override
    public boolean save(Audience objectToSave) {
        if (jdbcAudienceDao.findByName(objectToSave.getName()).isPresent()) {
            throw new NotSavedException(ErrorMessage.AUDIENCE_NOT_SAVED);
        } else {
            jdbcAudienceDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (jdbcAudienceDao.findById(id).isPresent()) {
            jdbcAudienceDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.AUDIENCE_NOT_DELETED);
        }
    }

    @Override
    public Optional<Audience> findById(long id) {
        return Optional.of(jdbcAudienceDao.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public Optional<Audience> findByName(String name) {
        return Optional.of(jdbcAudienceDao.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public List<Audience> findAll() {
        List<Audience> audienceList = jdbcAudienceDao.findAll();
        if(audienceList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return audienceList;
    }

    @Override
    public boolean update(Audience newObject) {
        if (jdbcAudienceDao.findByName(newObject.getName()).isPresent()) {
            jdbcAudienceDao.update(newObject);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.AUDIENCE_NOT_UPDATED);
        }
    }

}
