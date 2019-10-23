package academy.softserve.museum.entities.dto;

import java.sql.Date;

public class TimetableDto {
    private Date dateTimeFrom;
    private Date dateTimeTill;
    private Long employeeId;
    private Long excursionId;

    public TimetableDto(Date dateTimeFrom, Date dateTimeTill, Long employeeId, Long excursionId) {
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTill = dateTimeTill;
        this.employeeId = employeeId;
        this.excursionId = excursionId;
    }

    public Date getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(Date dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public Date getDateTimeTill() {
        return dateTimeTill;
    }

    public void setDateTimeTill(Date dateTimeTill) {
        this.dateTimeTill = dateTimeTill;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(Long excursionId) {
        this.excursionId = excursionId;
    }
}
