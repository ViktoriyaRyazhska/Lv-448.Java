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

@WebServlet(urlPatterns = "/excursions/delete-excursion/*")
public class DeleteExcursionServlet extends HttpServlet {

    private ExcursionService excursionService;

    @Override
    public void init() throws ServletException {
        excursionService = new ExcursionServiceImpl();
    }

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
