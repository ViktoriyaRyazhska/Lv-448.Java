package academy.softserve.museum.entities.statistic;

import academy.softserve.museum.entities.Excursion;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class, that contains Excursion statistic
 */
public class ExcursionStatistic {

    /**
     * Map, that contains Excursion as key and
     * the number of times this excursion was held
     * as a value.
     */
    private Map<Excursion, Integer> excursionCountMap;
    /**
     * statistic start date and time
     */
    private Date dateStart;
    /**
     * statistic end date and time
     */
    private Date dateEnd;

    /**
     * Constructor, that accepts all parameters
     */
    public ExcursionStatistic(Map<Excursion, Integer> excursionCountMap, Date dateStart, Date dateEnd) {
        this.excursionCountMap = excursionCountMap;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Constructor, that initialize map
     */
    public ExcursionStatistic() {
        excursionCountMap = new HashMap<>();
    }

    /**
     * @return excursion count map.
     */
    public Map<Excursion, Integer> getExcursionCountMap() {
        return excursionCountMap;
    }

    /**
     * @param excursionCountMap excursion count map
     */
    public void setExcursionCountMap(Map<Excursion, Integer> excursionCountMap) {
        this.excursionCountMap = excursionCountMap;
    }

    /**
     * @return statistic start date and time
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart statistic start date and time
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return statistic end date and time
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd statistic end date and time
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
