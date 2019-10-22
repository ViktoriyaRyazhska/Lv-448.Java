package academy.softserve.museum.servlet.excursion;

import academy.softserve.museum.entities.mappers.ExcursionStatsDtoMapper;
import academy.softserve.museum.services.ExcursionService;
import academy.softserve.museum.services.impl.ExcursionServiceImpl;
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

/**
 * Class processes requests for /excursions/statistics url.
 *
 * @version 1.0
 */
@WebServlet("/excursions/statistics")
public class ExcursionStatsServlet extends HttpServlet {

    private ExcursionService excursionService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        excursionService = new ExcursionServiceImpl();
    }

    /**
     * Method processes GET request for /excursions/statistics url
     * and returns /excursion-chart.jsp for visualization
     * of employee statistics.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/excursion-chart.jsp").include(req, resp);
    }

    /**
     * Method processes POST request for /excursions/statistics url
     * and returns /excursion-chart.jsp with excursion statistics
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

        req.setAttribute("statistics",
                Serializer.toJsonString(ExcursionStatsDtoMapper
                        .getExcursionStats(excursionService.findStatistic(dateTimeFrom, dateTimeTill))));

        req.getRequestDispatcher("/excursion-chart.jsp").include(req, resp);
    }

}
