package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Employee;
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

/**
 * Class processes requests for /exhibits/by-employee url.
 *
 * @version 1.0
 */
@WebServlet("/exhibits/by-employee")
public class ExhibitsByEmployee extends HttpServlet {

    private ExhibitService exhibitService;
    private EmployeeService employeeService;
    private AudienceService audienceService;

    /**
     * Method initializes required resources.
     */
    @Override
    public void init() {
        exhibitService = new ExhibitServiceImpl();
        employeeService = new EmployeeServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    /**
     * Method processes POST request for /exhibits/by-employee url
     * and returns list of exhibits by given employee.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee =
                employeeService.findByFullName(req.getParameter("firstName"), req.getParameter("lastName"));
        req.setAttribute("exhibits", exhibitService.findByEmployee(employee));
        req.setAttribute("audiences", audienceService.findAll());
        req.getRequestDispatcher("/exhibits.jsp").include(req,resp);
    }
}
