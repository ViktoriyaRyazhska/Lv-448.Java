package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.TimetableDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcTimetableDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.TimetableService;

import java.util.List;
import java.util.Optional;

public class TimetableServiceImpl implements TimetableService {

    private TimetableDao jdbcTimetableDao;

    public TimetableServiceImpl() {
        this.jdbcTimetableDao = DaoFactory.timetableDao();
    }

    public TimetableServiceImpl(JdbcTimetableDao jdbcTimetableDao) {
        this.jdbcTimetableDao = jdbcTimetableDao;
    }

    @Override
    public void save(Timetable objectToSave) {
        try {
            jdbcTimetableDao.save(objectToSave);
        }catch (Exception e){
            throw new NotSavedException(ErrorMessage.TIMETABLE_NOT_SAVED);
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (jdbcTimetableDao.findById(id).isPresent()) {
            jdbcTimetableDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.TIMETABLE_NOT_DELETED);
        }
    }

    @Override
    public Optional<Timetable> findById(long id) {
        return Optional.of(jdbcTimetableDao.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public List<Timetable> findAll() {
        List<Timetable> timetableList = jdbcTimetableDao.findAll();
        if(timetableList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return timetableList;
    }

    @Override
    public boolean update(Timetable newObject) {
        if (jdbcTimetableDao.findById(newObject.getId()).isPresent()) {
            jdbcTimetableDao.update(newObject);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.TIMETABLE_NOT_UPDATED);
        }
    }

}
