package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;
import academy.softserve.museum.util.PathParser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /exhibits/delete-exhibit url.
 *
 * @version 1.0
 */
@WebServlet(urlPatterns = "/exhibits/delete-exhibit/*")
public class DeleteExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init(ServletConfig config) {
        exhibitService = new ExhibitServiceImpl();
    }

    /**
     * Method processes GET request for /exhibits/delete-exhibit url
     * and passes id of an Exhibit, that should be deleted
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
            exhibitService.deleteById(id);
            req.setAttribute("successMessage", "Exhibit has been successfully deleted");
        } catch (RuntimeException e) {
            req.setAttribute("failureMessage", "Something went wrong!");
        }
        req.getRequestDispatcher("/exhibits").forward(req, resp);
    }

}
