package inc.softserve.servlets;

import inc.softserve.entities.Hotel;
import inc.softserve.services.intefaces.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@WebServlet(value = {"/available_hotels/*"})
public class AvailableHotelsServlet extends HttpServlet {

    private HotelService hotelService;

    @Override
    public void init() {
        hotelService = (HotelService) getServletContext().getAttribute("hotelService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate checkin = LocalDate.parse(req.getParameter("checkin"), DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                .plusDays(1);
        LocalDate checkout = LocalDate.parse(req.getParameter("checkout"), DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                .plusDays(1);
        Long cityId = Long.parseLong(req.getParameter("cityId"));
        String cityName = req.getParameter("cityName");
        Set<Hotel> hotels = hotelService.findAvailableHotelsInCity(cityId, checkin, checkout);
        req.setAttribute("hotels", hotels);
        req.setAttribute("city", cityName);
        req.setAttribute("cityId", cityId);
        req.getRequestDispatcher("/hotels.jsp").forward(req, resp);
    }
}
