package academy.softserve.museum.entities.mappers;

import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.entities.dto.ScheduleDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDtoMapper {
    public static List<ScheduleDto> getSchedule(List<Timetable> timetables) {
        List<ScheduleDto> schedule = new ArrayList<>();
        for (Timetable timetable : timetables) {
            ScheduleDto scheduleDto =
                    new ScheduleDto(
                            timetable.getId(),
                            timetable.getEmployee().getFirstName() + " " +timetable.getEmployee().getLastName(),
                            timetable.getExcursion().getName(),
                            new Date(timetable.getDateStart().getTime()).toGMTString(),
                            new Date(timetable.getDateEnd().getTime()).toGMTString()
                    );
            schedule.add(scheduleDto);
        }
        return schedule;
    }
}
