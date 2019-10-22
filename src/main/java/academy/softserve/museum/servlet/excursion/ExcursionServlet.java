package academy.softserve.museum.servlet.excursion;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.services.ExcursionService;
import academy.softserve.museum.services.impl.ExcursionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class processes requests for /excursions url.
 *
 * @version 1.0
 */
@WebServlet("/excursions")
public class ExcursionServlet extends HttpServlet {

    private ExcursionService excursionService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        excursionService = new ExcursionServiceImpl();
    }

    /**
     * Method processes GET request for /excursions url.
     * and returns /excursions.jsp with list of excursions
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Excursion> excursions;
        if(!excursionService.findAll().isEmpty()){
            excursions = excursionService.findAll();
        } else {
            excursions = new ArrayList<>();
        }
        req.setAttribute("excursions", excursions);
        req.getRequestDispatcher("/excursions.jsp").include(req,resp);
    }

}
