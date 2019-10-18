package inc.softserve.servlets;

import inc.softserve.dao.implementations.CountryDaoJdbc;
import inc.softserve.database.ConnectDb;
import inc.softserve.entities.Country;
import inc.softserve.services.implementations.CountryServiceImpl;
import inc.softserve.services.intefaces.CountryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(value = {"", "/index"})
public class WelcomeServlet extends HttpServlet {

    private CountryService countryService;

    @Override
    public void init() throws ServletException {
        countryService = new CountryServiceImpl(new CountryDaoJdbc(ConnectDb.connectBase()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Set<Country> countries = countryService.findAll();
        req.setAttribute("countries", countries);
        req.getRequestDispatcher("/index.jsp")
                .include(req, resp);
    }
}
