package academy.softserve.museum.servlet.employee;

import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;
import academy.softserve.museum.util.PathParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /employees/delete-employee url.
 *
 * @version 1.0
 */
@WebServlet(urlPatterns = "/employees/delete-employee/*")
public class DeleteEmployeeServlet extends HttpServlet {

    private EmployeeService employeeService;

    /**
     * Method initializes required resources.
     */
    @Override
    public void init() {
        employeeService = new EmployeeServiceImpl();
    }

    /**
     * Method processes GET request for /employees/delete-employee url
     * and passes id of an Employee, that should be deleted
     * to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long id = PathParser.getPathVariable(req.getPathInfo());
        try {
            employeeService.deleteById(id);
            req.setAttribute("successMessage", "Employee has been successfully deleted");
        } catch (RuntimeException e) {
            req.setAttribute("failureMessage", "Something went wrong!");
        }
        req.getRequestDispatcher("/employees").forward(req, resp);
    }
}
