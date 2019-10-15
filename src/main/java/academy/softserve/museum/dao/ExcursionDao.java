package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import java.sql.Date;
import java.util.Optional;

public interface ExcursionDao extends Crud<Excursion> {

    ExcursionStatistic findStatistic(Date dateStart, Date dateEnd);

    Optional<Excursion> findByName(String name);

}
