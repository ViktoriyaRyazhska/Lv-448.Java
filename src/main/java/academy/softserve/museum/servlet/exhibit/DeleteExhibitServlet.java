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

@WebServlet(urlPatterns = "/exhibits/delete-exhibit/*")
public class DeleteExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        exhibitService = new ExhibitServiceImpl();
    }

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
