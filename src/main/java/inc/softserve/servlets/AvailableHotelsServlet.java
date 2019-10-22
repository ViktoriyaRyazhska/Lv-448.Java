package inc.softserve.servlets;

import inc.softserve.entities.Hotel;
import inc.softserve.errors.ContextParameterNotFound;
import inc.softserve.services.intefaces.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@WebServlet(value = {"/available_hotels/*"})
public class AvailableHotelsServlet extends HttpServlet {

    private HotelService hotelService;

    @Override
    public void init() throws ServletException {
        hotelService = (HotelService) getServletContext().getAttribute("hotelService");
        if (hotelService == null){
            throw new ContextParameterNotFound("Context parameter has not been found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate checkin = LocalDate.parse(req.getParameter("checkin"));
        LocalDate checkout = LocalDate.parse(req.getParameter("checkout"));
        Long cityId = Long.parseLong(req.getParameter("city_id"));
        Set<Hotel> hotels = hotelService.findAvailableHotelsInCity(cityId, checkin, checkout);
        req.setAttribute("hotels", hotels);
        req.getRequestDispatcher("hotels.jsp").include(req, resp);
    }
}
