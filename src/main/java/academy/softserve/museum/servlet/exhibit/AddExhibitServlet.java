package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.ExhibitType;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/exhibits/add-exhibit")
public class AddExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;

    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("types", exhibitService.getTypes());
        req.setAttribute("audiences", audienceService.findAll());
        req.getRequestDispatcher("/add-exhibit.jsp").include(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String type = req.getParameter("type");
        String material = req.getParameter("material");
        String technic = req.getParameter("technic");
        int audience = Integer.parseInt(req.getParameter("audience"));
    }
}
