package academy.softserve.museum.servlet.schedule;


import academy.softserve.museum.services.TimetableService;
import academy.softserve.museum.services.impl.TimetableServiceImpl;
import academy.softserve.museum.entities.mappers.ScheduleDtoMapper;
import academy.softserve.museum.util.Serializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /schedule url
 *
 * @version 1.0
 */
@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {

    private TimetableService timetableService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() throws ServletException {
        timetableService = new TimetableServiceImpl();
    }

    /**
     * Method processes GET request for /schedule url
     * and returns /schedule.jsp with an excursion schedule
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("excursions", Serializer.toJsonString(ScheduleDtoMapper.getSchedule(timetableService.findAll())));
        req.getRequestDispatcher("/schedule.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
