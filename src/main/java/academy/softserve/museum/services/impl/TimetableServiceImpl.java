package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.impl.jdbc.JdbcTimetableDao;
import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.services.TimetableService;

import java.util.List;
import java.util.Optional;

public class TimetableServiceImpl implements TimetableService {

    public TimetableServiceImpl() {
    }

    public TimetableServiceImpl(
            JdbcTimetableDao jdbcTimetableDao) {
        this.jdbcTimetableDao = jdbcTimetableDao;
    }

    private JdbcTimetableDao jdbcTimetableDao;

    @Override
    public void save(Timetable objectToSave) {
        jdbcTimetableDao.save(objectToSave);
    }

    @Override
    public boolean deleteById(long id) {
        if (jdbcTimetableDao.findById(id).isPresent()) {
            jdbcTimetableDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Timetable> findById(long id) {
        return jdbcTimetableDao.findById(id);
    }

    @Override
    public List<Timetable> findAll() {
        return jdbcTimetableDao.findAll();
    }

    @Override
    public boolean update(Timetable newObject) {
        if (jdbcTimetableDao.findById(newObject.getId()).isPresent()) {
            jdbcTimetableDao.update(newObject);
            return true;
        } else {
            return false;
        }
    }

}
