package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.impl.jdbc.JdbcAudienceDao;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.services.AudienceService;
import java.util.List;
import java.util.Optional;

public class AudienceServiceImpl implements AudienceService {

    private JdbcAudienceDao jdbcAudienceDao;

    @Override
    public boolean save(Audience objectToSave) {
        if (jdbcAudienceDao.findByName(objectToSave.getName()).isPresent()) {
            return false;
        } else {
            jdbcAudienceDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (jdbcAudienceDao.findById(id).isPresent()) {
            return false;
        } else {
            jdbcAudienceDao.deleteById(id);
            return true;
        }
    }

    @Override
    public Optional<Audience> findById(long id) {
        return jdbcAudienceDao.findById(id);
    }

    @Override
    public Optional<Audience> findByName(String name) {
        return jdbcAudienceDao.findByName(name);
    }

    @Override
    public List<Audience> findAll() {
        return jdbcAudienceDao.findAll();
    }

    @Override
    public boolean update(Audience newObject) {
        if (jdbcAudienceDao.findByName(newObject.getName()).isPresent()) {
            jdbcAudienceDao.update(newObject);
            return true;
        } else {
            return false;
        }
    }

}
