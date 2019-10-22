package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class processes requests for /exhibits/add-exhibit url.
 *
 * @version 1.0
 */
@WebServlet("/exhibits/add-exhibit")
public class AddExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    /**
     * Method processes GET request for /exhibits/add-exhibit url
     * and returns /add-exhibit.jsp form
     * for adding new exhibits.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("types", exhibitService.getTypes());
        req.setAttribute("audiences", audienceService.findAll());
        req.getRequestDispatcher("/add-exhibit.jsp").include(req,resp);
    }

    /**
     * Method processes POST request for /exhibits/add-exhibit url
     * gets parameters from request object,
     * creates new Exhibit object and passes it to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String material = req.getParameter("material");
        String technic = req.getParameter("technic");
        ExhibitType exhibitType = ExhibitType.valueOf(req.getParameter("type"));

        Audience audience = new Audience();
        audience.setId(Long.parseLong(req.getParameter("audience")));
        Exhibit exhibit = new Exhibit(exhibitType, material, technic, name);

        try {
            exhibitService.save(exhibit);
            long id = exhibitService.findByName(exhibit.getName()).get().getId();
            exhibit.setId(id);
            exhibitService.updateExhibitAudience(exhibit, audience);
            resp.sendRedirect(req.getContextPath() + "/exhibits");
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/exhibits");
        }
    }
}
