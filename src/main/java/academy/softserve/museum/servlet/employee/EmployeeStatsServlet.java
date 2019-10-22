package academy.softserve.museum.servlet.employee;


import academy.softserve.museum.entities.statistic.EmployeeStatistic;;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;
import academy.softserve.museum.util.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;

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
 * Class processes requests for /employees/statistics url.
 *
 * @version 1.0
 */
@WebServlet("/employees/statistics")
public class EmployeeStatsServlet extends HttpServlet {

    private EmployeeService employeeService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        employeeService = new EmployeeServiceImpl();
    }

    /**
     * Method processes GET request for /employees/statistics url
     * and returns /employee-chart.jsp for visualization.
     * of employee statistics
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/employee-chart.jsp").include(req, resp);
    }

    /**
     * Method processes POST request for /employees/statistics url
     * and returns /employee-chart.jsp with employee statistics
     * for given date range.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date dateTimeFrom =
                new Date(LocalDateTime.parse(req.getParameter("from"))
                        .toInstant(ZoneOffset.of("+02:00"))
                        .toEpochMilli());

        Date dateTimeTill =
                new Date(LocalDateTime.parse(req.getParameter("till"))
                        .toInstant(ZoneOffset.of("+02:00"))
                        .toEpochMilli());

        EmployeeStatistic statistic = employeeService.findStatistic(dateTimeFrom, dateTimeTill);

        String tourGuidesJson = Serializer.toJsonString(statistic.getExcursionCount().keySet().stream().
                map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.toList()));

        String excursionsTotalJson = Serializer.toJsonString(statistic.getExcursionCount().values());
        String timeTotalJson = Serializer.toJsonString(statistic.getWorkTimeMap().values());

        req.setAttribute("tourGuides", tourGuidesJson);
        req.setAttribute("excursionsTotal", excursionsTotalJson);
        req.setAttribute("timeTotal", timeTotalJson);

        req.getRequestDispatcher("/employee-chart.jsp").include(req, resp);
    }
}
