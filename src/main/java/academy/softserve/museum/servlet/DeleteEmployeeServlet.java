package academy.softserve.museum.servlet;

import academy.softserve.museum.services.EmployeeService;
import academy.softserve.museum.services.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/delete-employee/*")
public class DeleteEmployeeServlet extends HttpServlet {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if(employeeService.deleteById(id)) {
            req.setAttribute("message", "Employee has been successfully deleted");
            resp.sendRedirect(req.getContextPath() + "/employees");
        } else {
            req.setAttribute("message", "Something went wrong!");
            resp.sendRedirect(req.getContextPath() + "/add-employee");
        }
    }
}
