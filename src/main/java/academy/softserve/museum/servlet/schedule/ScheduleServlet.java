package academy.softserve.museum.servlet.schedule;


import academy.softserve.museum.constant.MessageType;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.dto.TimetableDto;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.ExcursionService;
import academy.softserve.museum.services.TimetableService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;
import academy.softserve.museum.services.impl.ExcursionServiceImpl;
import academy.softserve.museum.services.impl.TimetableServiceImpl;
import academy.softserve.museum.entities.mappers.ScheduleDtoMapper;
import academy.softserve.museum.util.Serializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

/**
 * Class processes requests for /schedule url
 *
 * @version 1.0
 */
@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {

    private TimetableService timetableService;
    private ExcursionService excursionService;
    private EmployeeService employeeService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() throws ServletException {
        timetableService = new TimetableServiceImpl();
        excursionService = new ExcursionServiceImpl();
        employeeService = new EmployeeServiceImpl();
    }

    /**
     * Method processes GET request for /schedule url
     * and returns /schedule.jsp with an excursion schedule
     *
     * @param req  HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("excursions", Serializer.toJsonString(ScheduleDtoMapper.getSchedule(timetableService.findAll())));
        req.setAttribute("excursionList", excursionService.findAll());
        req.setAttribute("employees", employeeService
                .findAll()
                .stream()
                .filter(e -> e.getPosition() == EmployeePosition.TOUR_GUIDE)
                .collect(Collectors.toList()));
        req.getRequestDispatcher("/schedule.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long employeeId = Long.parseLong(req.getParameter("employee"));
        Long excursionId = Long.parseLong(req.getParameter("excursion"));

        Date dateTimeFrom =
                new Date(LocalDateTime.parse(req.getParameter("from"))
                        .toInstant(ZoneOffset.of("+03:00"))
                        .toEpochMilli());

        Date dateTimeTill =
                new Date(LocalDateTime.parse(req.getParameter("till"))
                        .toInstant(ZoneOffset.of("+03:00"))
                        .toEpochMilli());

        try {
            if (dateTimeFrom.compareTo(dateTimeTill) < 0) {
                TimetableDto dto = new TimetableDto(dateTimeFrom, dateTimeTill, employeeId, excursionId);
                timetableService.save(dto);
            }
        } catch (RuntimeException e) {
            req.setAttribute(MessageType.FAILURE, "Something went wrong!");
        } finally {
            req.setAttribute("excursions", Serializer.toJsonString(ScheduleDtoMapper.getSchedule(timetableService.findAll())));
            req.setAttribute("excursionList", excursionService.findAll());
            req.setAttribute("employees", employeeService
                    .findAll()
                    .stream()
                    .filter(e -> e.getPosition() == EmployeePosition.TOUR_GUIDE)
                    .collect(Collectors.toList()));
            req.getRequestDispatcher("/schedule.jsp").forward(req, resp);
        }
    }
}
