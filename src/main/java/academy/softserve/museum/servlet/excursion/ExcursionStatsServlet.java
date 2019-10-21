package academy.softserve.museum.servlet.excursion;

import academy.softserve.museum.entities.dto.ExcursionStatsDto;
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

@WebServlet("/excursions/statistics")
public class ExcursionStatsServlet extends HttpServlet {

    private ExcursionService excursionService;

    @Override
    public void init() throws ServletException {
        excursionService = new ExcursionServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/excursion-chart.jsp").include(req, resp);
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

        req.setAttribute("statistics",
                Serializer.toJsonString(ExcursionStatsDtoMapper
                        .getExcursionStats(excursionService.findStatistic(dateTimeFrom, dateTimeTill))));

        req.getRequestDispatcher("/excursion-chart.jsp").include(req, resp);
    }

}
