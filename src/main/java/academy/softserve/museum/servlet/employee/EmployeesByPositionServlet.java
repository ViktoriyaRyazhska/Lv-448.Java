package academy.softserve.museum.servlet.employee;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
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
 * Class processes requests for /employees/by-position.
 *
 * @version 1.0
 */
@WebServlet("/employees/by-position")
public class EmployeesByPositionServlet extends HttpServlet {

    private EmployeeService employeeService;

    /**
     * Method initializes required resources.
     */
    @Override
    public void init() {
        employeeService = new EmployeeServiceImpl();
    }

    /**
     * Method processes POST request for /employees/by-position url
     * and returns list of employees by given employee position.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeePosition position = EmployeePosition.valueOf(req.getParameter("position"));
        List<Employee> employeesByPosition = employeeService.findByPosition(position);
        if(position.equals(EmployeePosition.NONE)) {
            resp.sendRedirect(req.getContextPath() + "/employees");
        } else {
            req.setAttribute("employees", employeesByPosition);
            req.getRequestDispatcher("/employees.jsp").include(req, resp);
        }

    }
}
