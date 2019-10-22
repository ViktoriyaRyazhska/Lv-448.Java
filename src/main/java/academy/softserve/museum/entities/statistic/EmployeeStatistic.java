package academy.softserve.museum.entities.statistic;

import academy.softserve.museum.entities.Employee;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains Employee statistic
 */
public class EmployeeStatistic {

    /**
     * Map, that contains Employee as key and
     * his / her work time as a value
     */
    private Map<Employee, Integer> workTimeMap;
    /**
     * Map, that contains Employee as key and
     * his / her excursion count as a value
     */
    private Map<Employee, Integer> excursionCount;
    /**
     * statistic start date and time
     */
    private Date dateStart;
    /**
     * statistic end date and time
     */
    private Date dateEnd;

    /**
     * Constructor, that initialize maps
     */
    public EmployeeStatistic() {
        workTimeMap = new HashMap<>();
        excursionCount = new HashMap<>();
    }

    /**
     * Constructor, that accepts all parameters
     */
    public EmployeeStatistic(Map<Employee, Integer> workTimeMap, Map<Employee, Integer> excursionCount, Date dateStart, Date dateEnd) {
        this.workTimeMap = workTimeMap;
        this.excursionCount = excursionCount;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * @return work time map
     */
    public Map<Employee, Integer> getWorkTimeMap() {
        return workTimeMap;
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

    /**
     * @return excursion count map
     */
    public Map<Employee, Integer> getExcursionCount() {
        return excursionCount;
    }

    /**
     * @param excursionCount excursion count map
     */
    public void setExcursionCount(Map<Employee, Integer> excursionCount) {
        this.excursionCount = excursionCount;
    }

    /**
     * @param workTimeMap work time map
     */
    public void setWorkTimeMap(Map<Employee, Integer> workTimeMap) {
        this.workTimeMap = workTimeMap;
    }
}