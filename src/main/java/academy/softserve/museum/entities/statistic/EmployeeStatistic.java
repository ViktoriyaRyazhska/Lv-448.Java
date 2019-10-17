package academy.softserve.museum.entities.statistic;

import academy.softserve.museum.entities.Employee;

import java.sql.Date;
import java.util.Map;

public class EmployeeStatistic {
    private Map<Employee, Integer> workTimeMap;
    private Date dateStart;
    private Date dateEnd;

    public EmployeeStatistic(Map<Employee, Integer> workTimeMap, Date dateStart, Date dateEnd) {
        this.workTimeMap = workTimeMap;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public EmployeeStatistic() {
    }

    public Map<Employee, Integer> getWorkTimeMap() {
        return workTimeMap;
    }

    public EmployeeStatistic setWorkTimeMap(Map<Employee, Integer> workTimeMap) {
        this.workTimeMap = workTimeMap;
        return this;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public EmployeeStatistic setDateStart(Date dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public EmployeeStatistic setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }
}
