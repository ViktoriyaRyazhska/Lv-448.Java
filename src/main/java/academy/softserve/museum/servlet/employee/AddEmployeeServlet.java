package academy.softserve.museum.servlet.employee;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.dto.EmployeeDto;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /employees/add-employee url.
 *
 * @version 1.0
 */
@WebServlet("/employees/add-employee")
public class AddEmployeeServlet extends HttpServlet {

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
     * Method processes GET request for /employees/add-employee url
     * and returns /add-employee.jsp form
     * for adding new employees.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("audiences", audienceService.findAll());
        req.getRequestDispatcher("/add-employee.jsp").include(req,resp);
    }

    /**
     * Method processes POST request for /employees/add-employee url
     * gets parameters from request object,
     * creates new Employee object and passes it to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        EmployeePosition position = EmployeePosition.valueOf(req.getParameter("position"));

        Long audienceId = (Long.parseLong(req.getParameter("audience")));

        EmployeeDto employeeDto = new EmployeeDto(firstName, lastName, username, password, position, audienceId);

        try {
            employeeService.save(employeeDto);
            resp.sendRedirect(req.getContextPath() + "/employees");
        } catch (NotSavedException e) {
            resp.sendRedirect(req.getContextPath() + "/employees");
        }
    }
}
