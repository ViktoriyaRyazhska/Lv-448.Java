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

@WebServlet("/employees/by-position")
public class EmployeesByPositionServlet extends HttpServlet {

    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        employeeService = new EmployeeServiceImpl();
    }

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
