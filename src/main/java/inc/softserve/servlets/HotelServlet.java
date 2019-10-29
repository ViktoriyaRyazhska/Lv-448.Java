package inc.softserve.servlets;

import inc.softserve.entities.City;
import inc.softserve.entities.Hotel;
import inc.softserve.exceptions.ContextParameterNotFound;
import inc.softserve.services.intefaces.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

/**
 * Class processes requests for "/hotels/*"  url
 */
@WebServlet("/hotels/*")
public class HotelServlet extends HttpServlet {

    private HotelService hotelService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        hotelService = (HotelService) getServletContext().getAttribute("hotelService");
        if (hotelService == null){
            throw new ContextParameterNotFound();
        }
    }

    /**
     * Method processes GET request for /hotels/* url
     *
     * @param req  HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long cityId = Long.parseLong(req.getParameter("cityId"));
        String city = req.getPathInfo().replace("/", "");
        Set<Hotel> hotels = hotelService.findHotelsByCityId(cityId);
        req.setAttribute("city", city);
        req.setAttribute("cityId", cityId);
        req.setAttribute("hotels", hotels);
        req.getRequestDispatcher("/hotels.jsp").include(req, resp);
    }

    /**
     * Method processes POST request for /hotels/* url
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String country = req.getParameter("countryName");
        String city = req.getParameter("cityName");
        Set<Hotel> hotels = hotelService.findHotelsByCountryAndCity(country, city);
        Long cityId = hotels.stream()
                .findFirst()
                .map(Hotel::getCity)
                .map(City::getId)
                .orElse(null);
        req.setAttribute("city", city);
        req.setAttribute("hotels", hotels);
        req.setAttribute("cityId", cityId);
        req.getRequestDispatcher("/hotels.jsp").include(req, resp);
    }
}
