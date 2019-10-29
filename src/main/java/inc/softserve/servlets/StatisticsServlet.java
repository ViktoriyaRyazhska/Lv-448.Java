package inc.softserve.servlets;

import inc.softserve.entities.Country;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
import inc.softserve.entities.stats.HotelStats;
import inc.softserve.exceptions.ContextParameterNotFound;
import inc.softserve.services.intefaces.HotelStatsService;
import inc.softserve.services.intefaces.VisaStatsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;
/**
 * Class processes requests for "/statistics"  url
 */
@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {

    private VisaStatsService visaStatsService;
    private HotelStatsService hotelStatsService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        visaStatsService = (VisaStatsService) getServletContext().getAttribute("visaStatsService");
        if (visaStatsService == null){
            throw new ContextParameterNotFound();
        }
        hotelStatsService = (HotelStatsService) getServletContext().getAttribute("hotelStatsService");
        if (hotelStatsService == null){
            throw new ContextParameterNotFound();
        }
    }

    /**
     * Method processes GET request for /statistics url
     *
     * @param req  HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usr user = (Usr) session.getAttribute("user");
        List<HotelStats> hotelsList = hotelStatsService.calcHotelStats();
        Set<Country> visitedCountries = visaStatsService.visitedCountries(user.getId());
        req.setAttribute("statisticCountry", visaStatsService.countVisasIssuedByAllCountry());
        req.setAttribute("visasNumbers", visaStatsService.countVisasByUserEmail(user.getEmail()).orElse(0));
        req.setAttribute("hotelsList", hotelsList);
        req.setAttribute("visitedCountries", visitedCountries);
        req.getRequestDispatcher("/statistics.jsp").include(req, resp);
    }
}
