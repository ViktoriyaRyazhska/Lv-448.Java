package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exhibits-by-audience")
public class ExhibitsByAudience extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Audience audience = new Audience();
        audience.setId(Integer.parseInt(req.getParameter("audience")));
        req.setAttribute("audiences", audienceService.findAll());
        req.setAttribute("exhibits", exhibitService.findByAudience(audience));
        req.getRequestDispatcher("/exhibits.jsp").include(req, resp);
    }

}
