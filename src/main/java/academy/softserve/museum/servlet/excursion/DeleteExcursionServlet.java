package academy.softserve.museum.servlet.excursion;

import academy.softserve.museum.services.ExcursionService;
import academy.softserve.museum.services.impl.ExcursionServiceImpl;
import academy.softserve.museum.util.PathParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /excursions/delete-excursion url.
 *
 * @version 1.0
 */
@WebServlet(urlPatterns = "/excursions/delete-excursion/*")
public class DeleteExcursionServlet extends HttpServlet {

    private ExcursionService excursionService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() throws ServletException {
        excursionService = new ExcursionServiceImpl();
    }

    /**
     * Method processes GET request for /excursions/delete-excursion url
     * and passes id of an Excursion, that should be deleted
     * to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = PathParser.getPathVariable(req.getPathInfo());
        try {
            excursionService.deleteById(id);
            req.setAttribute("successMessage", "Excursion has been successfully deleted");
        } catch (RuntimeException e) {
            req.setAttribute("failureMessage", "Something went wrong!");
        }
        req.getRequestDispatcher("/excursions").forward(req, resp);
    }

}
