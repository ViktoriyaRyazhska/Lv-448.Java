package academy.softserve.museum.entities.dto;

public class ScheduleDto {
    private long id;
    private String tourGuideFullName;
    private String excursionName;
    private String dateStart;
    private String dateEnd;

    public ScheduleDto(long id, String tourGuideFullName, String excursionName, String dateStart, String dateEnd) {
        this.id = id;
        this.tourGuideFullName = tourGuideFullName;
        this.excursionName = excursionName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTourGuideFullName() {
        return tourGuideFullName;
    }

    public void setTourGuideFullName(String tourGuideFullName) {
        this.tourGuideFullName = tourGuideFullName;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
