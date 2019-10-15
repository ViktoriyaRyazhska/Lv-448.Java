package academy.softserve.museum.services;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ExcursionService {

    boolean save(Excursion objectToSave);

    boolean deleteById(long id);

    Optional<Excursion> findById(long id);

    Optional<Excursion> findByName(String name);

    List<Excursion> findAll();

    boolean update(Excursion newObject);

    ExcursionStatistic findStatistic(Date dateStart, Date dateEnd);

}
