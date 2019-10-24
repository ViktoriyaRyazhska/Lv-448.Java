package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.ExcursionDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.ExcursionService;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class ExcursionServiceImpl implements ExcursionService {

    private ExcursionDao excursionDao;

    /**
     * Default constructor
     */
    public ExcursionServiceImpl() {
        this.excursionDao = DaoFactory.excursionDao();
    }

    /**
     * Constructor with 1 parameters
     *
     * @param excursionDao
     */
    public ExcursionServiceImpl(ExcursionDao excursionDao) {
        this.excursionDao = excursionDao;
    }

    /**
     * Method for saving object Excursion in database
     *
     * @return true if the save was successful
     */
    @Override
    public boolean save(Excursion objectToSave) throws NotSavedException {
        if (excursionDao.findByName(objectToSave.getName()).isPresent()) {
            throw new NotSavedException(ErrorMessage.EXCURSION_NOT_SAVED);
        } else {
            excursionDao.save(objectToSave);
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
        if (excursionDao.findById(id).isPresent()) {
            excursionDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EXCURSION_NOT_DELETED);
        }
    }

    /**
     * Method for deleting object Excursion by id
     *
     * @return true if the delete was successful
     */
    @Override
    public Optional<Excursion> findById(long id) {
        return Optional.of(excursionDao.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method for finding Excursion by name
     *
     * @param name of excursion
     * @return Excursion object wrapped in Optional
     */
    @Override
    public Optional<Excursion> findByName(String name) {
        return Optional.of(excursionDao.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method, that returns all objects of Excursion
     *
     * @return list of Excursion
     */
    @Override
    public List<Excursion> findAll() {
        List<Excursion> excursionList = excursionDao.findAll();
        if (excursionList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return excursionList;
    }

    /**
     * Method, that updates given object Excursion
     *
     * @return true if the update was successful
     */
    @Override
    public boolean update(Excursion excursion) {
        if (excursionDao.findByName(excursion.getName()).isPresent()) {
            excursionDao.update(excursion);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EXCURSION_NOT_UPDATED);
        }
    }

    /**
     * Method for finding statistic for all Employees. Statistic contains work time in hours and
     * excursion count for each Employee
     *
     * @return statistic
     */
    @Override
    public ExcursionStatistic findStatistic(Date dateStart, Date dateEnd) {
        try {
            return excursionDao.findStatistic(dateStart, dateEnd);
        } catch (Exception e) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
    }

    /**
     * Method for finding available Excursions in some period. Excursion is available if it starts
     * in given period.
     *
     * @param dateStart period start date and time.
     * @param dateEnd period end date and time.
     * @return list of available Excursions.
     */
    @Override
    public List<Excursion> findAvailable(Date dateStart, Date dateEnd) {
        List<Excursion> excursionList = excursionDao.findAvailable(dateStart, dateEnd);
        if (excursionList.size() < 1) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return excursionList;
    }
}
