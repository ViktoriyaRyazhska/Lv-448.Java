package academy.softserve.museum.servlet.employee;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Class processes requests for /employees url.
 *
 * @version 1.0
 */
@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    private EmployeeService employeeService = new EmployeeServiceImpl();

    /**
     * Method initializes required resources.
     */
    @Override
    public void init() {
        employeeService = new EmployeeServiceImpl();
    }

    /**
     * Method processes GET request for /employees url.
     * and returns /employees.jsp
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = employeeService.findAll();
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/employees.jsp").include(req,resp);
    }

}
