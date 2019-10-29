package inc.softserve.servlets;

import inc.softserve.entities.Country;
import inc.softserve.exceptions.ContextParameterNotFound;
import inc.softserve.services.intefaces.CountryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Class processes requests for "", "/index  url
 */
@WebServlet(value = {"", "/index"})
public class WelcomeServlet extends HttpServlet {

    private CountryService countryService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        countryService = (CountryService) getServletContext().getAttribute("countryService");
        if (countryService == null){
            throw new ContextParameterNotFound();
        }
    }

    /**
     * Method processes GET request for "","/index" url
     *
     * @param req  HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Set<Country> countries = countryService.findCountriesAndTheirCities();
        req.setAttribute("countries", countries);
        req
                .getRequestDispatcher("/index.jsp")
                .include(req, resp);
    }
}
