package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.impl.jdbc.JdbcExcursionDao;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import academy.softserve.museum.services.ExcursionService;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class ExcursionServiceImpl implements ExcursionService {

    private JdbcExcursionDao jdbcExcursionDao;

    @Override
    public boolean save(Excursion objectToSave) {
        if (jdbcExcursionDao.findByName(objectToSave.getName()).isPresent()) {
            return false;
        } else {
            jdbcExcursionDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (jdbcExcursionDao.findById(id).isPresent()) {
            return false;
        } else {
            jdbcExcursionDao.deleteById(id);
            return true;
        }
    }

    @Override
    public Optional<Excursion> findById(long id) {
        return jdbcExcursionDao.findById(id);
    }

    @Override
    public Optional<Excursion> findByName(String name) {
        return jdbcExcursionDao.findByName(name);
    }

    @Override
    public List<Excursion> findAll() {
        return jdbcExcursionDao.findAll();
    }

    @Override
    public boolean update(Excursion newObject) {
        if (jdbcExcursionDao.findByName(newObject.getName()).isPresent()) {
            jdbcExcursionDao.update(newObject);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ExcursionStatistic findStatistic(Date dateStart, Date dateEnd) {
        return jdbcExcursionDao.findStatistic(dateStart, dateEnd);
    }
}
