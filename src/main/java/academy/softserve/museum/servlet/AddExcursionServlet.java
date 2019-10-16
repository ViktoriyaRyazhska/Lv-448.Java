package academy.softserve.museum.servlet;

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

@WebServlet("/add-excursion")
public class AddExcursionServlet extends HttpServlet {

    private final ExcursionService excursionService = new ExcursionServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/add-excursion.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
//        String dateTime = req.getParameter("dateTime");
//        int duration = Integer.parseInt(req.getParameter("duration"));
        Excursion excursion = new Excursion(name);
        if(excursionService.save(excursion)){
            req.setAttribute("message", "Excursion has been successfully added");
            resp.sendRedirect(req.getContextPath() + "/excursions");
        } else {
            req.setAttribute("message", "Something went wrong!");
            resp.sendRedirect(req.getContextPath() + "/excursions");
        }
    }
}