package academy.softserve.museum.entities.statistic;

import academy.softserve.museum.entities.Employee;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class EmployeeStatistic {
    private Map<Employee, Integer> workTimeMap;
    private Map<Employee, Integer> excursionCount;
    private Date dateStart;
    private Date dateEnd;

    public EmployeeStatistic() {
        workTimeMap = new HashMap<>();
        excursionCount = new HashMap<>();
    }

    public EmployeeStatistic(Map<Employee, Integer> workTimeMap, Map<Employee, Integer> excursionCount, Date dateStart, Date dateEnd) {
        this.workTimeMap = workTimeMap;
        this.excursionCount = excursionCount;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Map<Employee, Integer> getWorkTimeMap() {
        return workTimeMap;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Map<Employee, Integer> getExcursionCount() {
        return excursionCount;
    }

    public void setExcursionCount(Map<Employee, Integer> excursionCount) {
        this.excursionCount = excursionCount;
    }

    public void setWorkTimeMap(Map<Employee, Integer> workTimeMap) {
        this.workTimeMap = workTimeMap;
    }
}
