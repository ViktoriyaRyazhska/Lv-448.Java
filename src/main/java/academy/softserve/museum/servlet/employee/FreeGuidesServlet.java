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
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/employees/free-guides")
public class FreeGuidesServlet extends HttpServlet {

    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        employeeService = new EmployeeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date dateTimeFrom =
                new Date(LocalDateTime.parse(req.getParameter("from"))
                        .toInstant(ZoneOffset.of("+02:00"))
                        .toEpochMilli());

        Date dateTimeTill =
                new Date(LocalDateTime.parse(req.getParameter("till"))
                        .toInstant(ZoneOffset.of("+02:00"))
                        .toEpochMilli());

        req.setAttribute("employees",
                employeeService.findAvailable(dateTimeFrom, dateTimeTill)
                        .stream()
                        .filter(e -> e.getPosition() == EmployeePosition.TOUR_GUIDE).collect(Collectors.toList()));

        req.getRequestDispatcher("/employees.jsp").include(req, resp);
    }

}
