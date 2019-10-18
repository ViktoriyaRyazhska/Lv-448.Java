package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-exhibit")
public class AddExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;

    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
