package inc.softserve.servlets;

import inc.softserve.entities.stats.RoomStats;
import inc.softserve.services.intefaces.RoomStatsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class processes requests for "/room_statistics/"  url
 */
@WebServlet(value = {"/room_statistics/"})
public class RoomStatisticsServlet extends HttpServlet {

    private RoomStatsService roomStatsService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        roomStatsService = (RoomStatsService) getServletContext().getAttribute("roomStatsService");
    }

    /**
     * Method processes GET request for /room_statistics/ url
     *
     * @param req  HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long hotelId = Long.parseLong(req.getParameter("hotel_id"));
        String hotelName = req.getParameter("hotel_name");
        LocalDate startPeriod = LocalDate.parse(req.getParameter("start_period"), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate endPeriod = LocalDate.parse(req.getParameter("end_period"), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        List<RoomStats> roomStats = roomStatsService.getRoomStats(hotelId, startPeriod, endPeriod);
        req.setAttribute("hotel_name", hotelName);
        req.setAttribute("room_stats", roomStats);
        req.getRequestDispatcher("/room_statistics.jsp").include(req, resp);
    }
}
