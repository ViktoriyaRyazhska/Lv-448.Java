package academy.softserve.museum.servlet.excursion;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.services.ExcursionService;
import academy.softserve.museum.services.impl.ExcursionServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/excursions/add-excursion")
public class AddExcursionServlet extends HttpServlet {

    private ExcursionService excursionService;

    @Override
    public void init() throws ServletException {
        excursionService = new ExcursionServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-excursion.jsp").include(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        Excursion excursion = new Excursion(name);
        if(excursionService.save(excursion)){
            req.setAttribute("message", "Excursion has been successfully added");
        } else {
            req.setAttribute("message", "Something went wrong!");
        }
        resp.sendRedirect(req.getContextPath() + "/excursions");
    }
}