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

@WebServlet(urlPatterns = "/delete-employee/*")
public class DeleteEmployeeServlet extends HttpServlet {

    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        employeeService = new EmployeeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = PathParser.getPathVariable(req.getPathInfo());
        if(employeeService.deleteById(id)) {
            req.setAttribute("message", "Employee has been successfully deleted");
        } else {
            req.setAttribute("message", "Something went wrong!");
        }
        resp.sendRedirect(req.getContextPath() + "/employees");
    }
}
