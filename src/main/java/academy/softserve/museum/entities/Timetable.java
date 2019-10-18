package academy.softserve.museum.entities;

import java.sql.Date;

public class Timetable extends Entity {
    private Employee employee;
    private Excursion excursion;
    private Date dateStart;
    private Date dateEnd;

    public Timetable(long id, Employee employee, Excursion excursion, Date dateStart, Date dateEnd) {
        super(id);
        this.employee = employee;
        this.excursion = excursion;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Timetable(Employee employee, Excursion excursion, Date dateStart, Date dateEnd) {
        this.employee = employee;
        this.excursion = excursion;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Timetable() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public Timetable setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public Timetable setExcursion(Excursion excursion) {
        this.excursion = excursion;
        return this;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Timetable setDateStart(Date dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Timetable setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "employee=" + employee +
                ", excursion=" + excursion +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
