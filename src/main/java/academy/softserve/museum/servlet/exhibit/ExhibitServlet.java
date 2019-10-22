package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /exhibits url.
 *
 * @version 1.0
 */
@WebServlet("/exhibits")
public class ExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;

    /**
     * Method initializes required resources.
     */
    @Override
    public void init() {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    /**
     * Method processes GET request for /exhibits url.
     * and returns /exhibits.jsp with list of exhibits
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("audiences", audienceService.findAll());
        req.setAttribute("exhibits", exhibitService.findAll());
        req.getRequestDispatcher("/exhibits.jsp").include(req, resp);
    }

}
