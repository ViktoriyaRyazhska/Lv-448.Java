package academy.softserve.museum.servlet;

import academy.softserve.museum.entities.Employee;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/excursions")
public class ExcursionServlet extends HttpServlet {

    private final ExcursionService excursionService = new ExcursionServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Excursion> excursions;
        if(!excursionService.findAll().isEmpty()){
            excursions = excursionService.findAll();
        } else {
            excursions = new ArrayList<>();
        }
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/excursions.jsp");
        req.setAttribute("excursions", excursions);
        requestDispatcher.forward(req, resp);
    }

}
