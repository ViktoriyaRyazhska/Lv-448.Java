package academy.softserve.museum.servlet.excursion;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.services.ExcursionService;
import academy.softserve.museum.services.impl.ExcursionServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /excursions/add-excursion url.
 *
 * @version 1.0
 */
@WebServlet("/excursions/add-excursion")
public class AddExcursionServlet extends HttpServlet {

    private ExcursionService excursionService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        excursionService = new ExcursionServiceImpl();
    }

    /**
     * Method processes GET request for /excursions/add-excursion url
     * and returns /add-excursion.jsp form
     * for adding new excursions.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-excursion.jsp").include(req,resp);
    }

    /**
     * Method processes POST request for /excursions/add-excursion url
     * gets parameters from request object,
     * creates new Excursion object and passes it to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        Excursion excursion = new Excursion(name);
        try {
            excursionService.save(excursion);
            req.setAttribute("successMessage", "Excursion has been successfully added");
            resp.sendRedirect(req.getContextPath() + "/excursions");
        } catch (NotSavedException e) {
            req.setAttribute("failureMessage", "Something went wrong!");
            resp.sendRedirect(req.getContextPath() + "/excursions");
        }
    }
}