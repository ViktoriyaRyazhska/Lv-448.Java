package academy.softserve.museum;

import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.Excursion;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.of(1999, 1, 1, 1, 1);
        LocalDateTime end = LocalDateTime.of(2020, 1, 1, 1, 1);

        Date s = new Date(start.toInstant(ZoneOffset.of("+02:00")).toEpochMilli());
        Date e = new Date(end.toInstant(ZoneOffset.of("+02:00")).toEpochMilli());

        DaoFactory.excursionDao().update(new Excursion(5, "test"));
    }
}
