package inc.softserve.servlets;

import inc.softserve.entities.Hotel;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

/**
 * Class processes requests for "/available_hotels/*"  url
 */
@WebServlet(value = {"/available_hotels/*"})
public class AvailableHotelsServlet extends HttpServlet {

    private HotelService hotelService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        hotelService = (HotelService) getServletContext().getAttribute("hotelService");
    }

    /**
     * Method processes GET request for /available_hotels/* url
     *
     * @param req  HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate checkin;
        LocalDate checkout;
        try {
            checkin = LocalDate.parse(req.getParameter("checkin"), DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    .plusDays(1);
            checkout = LocalDate.parse(req.getParameter("checkout"), DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    .plusDays(1);
        } catch (DateTimeParseException e){
            resp.getWriter().println("");
            return;
        }
        Long cityId = Long.parseLong(req.getParameter("cityId"));
        String cityName = req.getParameter("cityName");
        Set<Hotel> hotels;
        try {
            hotels = hotelService.findAvailableHotelsInCity(cityId, checkin, checkout);
        } catch (InvalidTimePeriod e){
            resp.getWriter().println("<script>alert('You have entered invalid time period')</script>");
            return;
        }
        req.setAttribute("hotels", hotels);
        req.setAttribute("city", cityName);
        req.setAttribute("cityId", cityId);
        req.getRequestDispatcher("/hotels.jsp").forward(req, resp);
    }
}
