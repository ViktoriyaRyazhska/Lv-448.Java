package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/exhibits")
public class ExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;

    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Exhibit> exhibits = exhibitService.findAll();
        for (Exhibit e:exhibits) {
            System.out.println(e.toString());
        }
        req.setAttribute("exhibits", exhibits);
        req.getRequestDispatcher("/exhibits.jsp").include(req, resp);
    }

}
