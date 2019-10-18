package academy.softserve.museum.servlet;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;
import academy.softserve.museum.util.PathParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/update-employee/*")
public class UpdateEmployeeServlet extends HttpServlet {

    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        employeeService = new EmployeeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long pathVariable = PathParser.getPathVariable(req.getPathInfo());
        Employee employee = employeeService.findById(pathVariable).get();
        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/update-employee.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Integer.parseInt(req.getParameter("id"));
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        EmployeePosition position = EmployeePosition.valueOf(req.getParameter("position"));
        //int audience = Integer.parseInt(req.getParameter("audience"));

        Employee employee = new Employee(id, firstName, lastName, position, username, password);

        employeeService.update(employee);
        resp.sendRedirect(req.getContextPath() + "/employees");
    }
}
