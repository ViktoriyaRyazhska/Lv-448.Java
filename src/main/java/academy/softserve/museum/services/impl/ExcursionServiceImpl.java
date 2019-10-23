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

    private ExcursionDao excursionDao = DaoFactory.excursionDao();

    @Override
    public boolean save(Excursion objectToSave) {
        if (excursionDao.findByName(objectToSave.getName()).isPresent()) {
            throw new NotSavedException(ErrorMessage.EXCURSION_NOT_SAVED);
        } else {
            excursionDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (excursionDao.findById(id).isPresent()) {
            excursionDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EXCURSION_NOT_DELETED);
        }
    }

    @Override
    public Optional<Excursion> findById(long id) {
        return Optional.of(excursionDao.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public Optional<Excursion> findByName(String name) {
        return Optional.of(excursionDao.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public List<Excursion> findAll() {
        List<Excursion> excursionList = excursionDao.findAll();
        if(excursionList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return excursionList;
    }

    @Override
    public boolean update(Excursion newObject) {
        if (excursionDao.findByName(newObject.getName()).isPresent()) {
            excursionDao.update(newObject);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EXCURSION_NOT_UPDATED);
        }
    }

    @Override
    public ExcursionStatistic findStatistic(Date dateStart, Date dateEnd) {
        try {
            return excursionDao.findStatistic(dateStart, dateEnd);
        } catch (Exception e){
            throw new  NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
    }

    @Override
    public List<Excursion> findAvailable(Date dateStart, Date dateEnd) {
        List<Excursion> excursionList = excursionDao.findAvailable(dateStart, dateEnd);
        if(excursionList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return excursionList;
    }
}
