package inc.softserve.servlets;

import inc.softserve.dao.implementations.CountryDaoJdbc;
import inc.softserve.dao.implementations.UsrDaoJdbc;
import inc.softserve.dao.implementations.VisaDaoJdbc;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.database.ConnectDb;
import inc.softserve.entities.Usr;
import inc.softserve.entities.stats.HotelStats;
import inc.softserve.services.implementations.VisaStatsServiceImpl;
import inc.softserve.services.intefaces.HotelStatsService;
import inc.softserve.services.intefaces.VisaStatsService;
import org.apache.catalina.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@WebServlet("/statistic")
public class StatisticServlet extends HttpServlet {

    private VisaStatsService visaStatsService;
    private HotelStatsService hotelStatsService;

    @Override
    public void init() throws ServletException {
        visaStatsService = (VisaStatsService) getServletContext().getAttribute("visaStatsService");
        hotelStatsService = (HotelStatsService) getServletContext().getAttribute("hotelStatsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usr user = (Usr) session.getAttribute("user");
        List<HotelStats> hotelsList = hotelStatsService.calcHotelStats();
        req.setAttribute("statisticCountry", visaStatsService.countVisasIssuedByAllCountry());
        req.setAttribute("statisticByUser", visaStatsService.countVisasByUserEmail(user.getEmail()));
        req.setAttribute("hotelsList", hotelsList);
        req.getRequestDispatcher("/statistic.jsp")
                .include(req, resp);
    }

}
