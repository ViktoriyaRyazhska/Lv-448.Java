package academy.softserve.museum.servlet;

import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Employee;
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
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = employeeService.findAll();
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/employees.jsp");
        req.setAttribute("employees", employees);
        requestDispatcher.forward(req, resp);
    }

}
