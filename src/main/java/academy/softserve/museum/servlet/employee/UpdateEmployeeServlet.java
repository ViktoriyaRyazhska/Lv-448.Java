package academy.softserve.museum.servlet.employee;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;
import academy.softserve.museum.util.PathParser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class processes requests for /employees/update-employee url
 *
 * @version 1.0
 */
@WebServlet(urlPatterns = "/employees/update-employee/*")
public class UpdateEmployeeServlet extends HttpServlet {

    private EmployeeService employeeService;
    private AudienceService audienceService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        employeeService = new EmployeeServiceImpl();
        audienceService = new AudienceServiceImpl();
    }

    /**
     * Method processes GET request for /employees/update-employee url
     * and returns /update-employee.jsp form
     * for updating existing employee data
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long pathVariable = PathParser.getPathVariable(req.getPathInfo());
        Employee employee = employeeService.findById(pathVariable).get();
        req.setAttribute("employee", employee);
        req.setAttribute("audiences", audienceService.findAll());
        req.getRequestDispatcher("/update-employee.jsp").include(req, resp);
    }

    /**
     * Method processes POST request for /employees/update-employee url
     * gets parameters from request object,
     * creates new Employee object and passes it to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Integer.parseInt(req.getParameter("id"));

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        EmployeePosition position = EmployeePosition.valueOf(req.getParameter("position"));
        int audienceId = Integer.parseInt(req.getParameter("audience"));

        Audience audience = new Audience();
        audience.setId(audienceId);

        Employee employee = new Employee(id, firstName, lastName, position, username, password);

        employeeService.update(employee);
        employeeService.updateEmployeeAudience(employee, audience);
        resp.sendRedirect(req.getContextPath() + "/employees");
    }
}