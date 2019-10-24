package inc.softserve.servlets;

import inc.softserve.entities.Usr;
import inc.softserve.entities.stats.HotelStats;
import inc.softserve.entities.stats.RoomStats;
import inc.softserve.exceptions.ContextParameterNotFound;
import inc.softserve.services.intefaces.HotelStatsService;
import inc.softserve.services.intefaces.RoomStatsService;
import inc.softserve.services.intefaces.VisaStatsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {

    private VisaStatsService visaStatsService;
    private HotelStatsService hotelStatsService;
    private RoomStatsService roomStatsService;


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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usr user = (Usr) session.getAttribute("user"); // TODO - use real data instead of the stub
        List<HotelStats> hotelsList = hotelStatsService.calcHotelStats();
        req.setAttribute("statisticCountry", visaStatsService.countVisasIssuedByAllCountry());
       // req.setAttribute("statisticByUser", visaStatsService.countVisasByUserEmail(user.getEmail()));
        req.setAttribute("statisticByUser", visaStatsService.countVisasByUserEmail("user@gmail.com").orElseThrow());
        req.setAttribute("hotelsList", hotelsList);
        req.getRequestDispatcher("/statistics.jsp").include(req, resp);
    }
}
