package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Exhibit;
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
import java.util.List;

@WebServlet("/exhibits")
public class ExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;

    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("audiences", audienceService.findAll());
        req.setAttribute("exhibits", exhibitService.findAll());
        req.getRequestDispatcher("/exhibits.jsp").include(req, resp);
    }

}
