package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.TimetableDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcTimetableDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.TimetableService;
import java.util.List;
import java.util.Optional;

public class TimetableServiceImpl implements TimetableService {

    private TimetableDao timetableDao;

    /**
     * Default constructor
     */
    public TimetableServiceImpl() {
        this.timetableDao = DaoFactory.timetableDao();
    }

    /**
     * Constructor with 1 parameters
     *
     * @param timetableDao
     */
    public TimetableServiceImpl(JdbcTimetableDao timetableDao) {
        this.timetableDao = timetableDao;
    }

    /**
     * Method for saving object Timetable in database
     *
     * @return true if the save was successful
     */
    @Override
    public void save(Timetable timetable) {
        try {
            timetableDao.save(timetable);
        } catch (Exception e) {
            throw new NotSavedException(ErrorMessage.TIMETABLE_NOT_SAVED);
        }
    }

    /**
     * Method for deleting object by id
     *
     * @return true if the delete was successful
     */
    @Override
    public boolean deleteById(long id) {
        if (timetableDao.findById(id).isPresent()) {
            timetableDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.TIMETABLE_NOT_DELETED);
        }
    }

    /**
     * Method for deleting object Timetable by id
     *
     * @return true if the delete was successful
     */
    @Override
    public Optional<Timetable> findById(long id) {
        return Optional.of(timetableDao.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method, that returns all objects of Timetable
     *
     * @return list of Timetable
     */
    @Override
    public List<Timetable> findAll() {
        List<Timetable> timetableList = timetableDao.findAll();
        if (timetableList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return timetableList;
    }

    /**
     * Method, that updates given object Timetable
     *
     * @return true if the update was successful
     */
    @Override
    public boolean update(Timetable timetable) {
        if (timetableDao.findById(timetable.getId()).isPresent()) {
            timetableDao.update(timetable);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.TIMETABLE_NOT_UPDATED);
        }
    }

}
