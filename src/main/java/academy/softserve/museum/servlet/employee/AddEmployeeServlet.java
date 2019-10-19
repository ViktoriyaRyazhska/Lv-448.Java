package academy.softserve.museum.servlet.employee;

import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employees/add-employee")
public class AddEmployeeServlet extends HttpServlet {

    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
         employeeService = new EmployeeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-employee.jsp").include(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        EmployeePosition position = EmployeePosition.valueOf(req.getParameter("position"));
        int audience = Integer.parseInt(req.getParameter("audience"));

        Employee employee = new Employee(firstname, lastname, position, username, password);

        if(employeeService.save(employee)) {
            req.setAttribute("message", "Employee has been successfully added");
            resp.sendRedirect(req.getContextPath() + "/employees");
        } else {
            req.setAttribute("message", "Something went wrong!");
            resp.sendRedirect(req.getContextPath() + "/add-employee");
        }

    }
}
