package academy.softserve.museum.servlet.employee;


import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@WebServlet("/employee-statistics")
public class EmployeeStatsServlet extends HttpServlet {

    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        employeeService = new EmployeeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/employee-chart.jsp").include(req, resp);
    }

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

        ObjectMapper mapper = new ObjectMapper();

        String tourGuidesJson = mapper.writeValueAsString(
                statistic.getExcursionCount().keySet().stream().
                        map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.toList()));

        String excursionsTotalJson = mapper.writeValueAsString(statistic.getExcursionCount().values());
        String timeTotalJson = mapper.writeValueAsString(statistic.getWorkTimeMap().values());

        req.setAttribute("tourGuides", tourGuidesJson);
        req.setAttribute("excursionsTotal", excursionsTotalJson);
        req.setAttribute("timeTotal", timeTotalJson);

        req.getRequestDispatcher("/employee-chart.jsp").include(req, resp);
    }
}
