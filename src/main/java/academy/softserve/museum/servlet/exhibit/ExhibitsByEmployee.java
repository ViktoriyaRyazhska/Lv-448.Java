package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/exhibits-by-employee")
public class ExhibitsByEmployee extends HttpServlet {

    private ExhibitService exhibitService;
    private EmployeeService employeeService;
    private AudienceService audienceService;

    @Override
    public void init() {
        exhibitService = new ExhibitServiceImpl();
        employeeService = new EmployeeServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee =
                employeeService.findByFullName(req.getParameter("firstName"), req.getParameter("lastName"));
        req.setAttribute("exhibits", exhibitService.findByEmployee(employee));
        req.setAttribute("audiences", audienceService.findAll());
        req.getRequestDispatcher("/exhibits.jsp").include(req,resp);
    }
}
