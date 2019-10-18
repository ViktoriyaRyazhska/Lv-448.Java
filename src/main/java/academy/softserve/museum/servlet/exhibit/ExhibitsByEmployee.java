package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ExhibitsByEmployee extends HttpServlet {

    private ExhibitService exhibitService;

    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = new Employee()
                .setFirstName(req.getParameter("firstName"))
                .setLastName(req.getParameter("lastName"));
        req.setAttribute("exhibits", exhibitService.findByEmployee(employee));
        req.getRequestDispatcher("/exhibits.jsp").include(req,resp);
    }
}
