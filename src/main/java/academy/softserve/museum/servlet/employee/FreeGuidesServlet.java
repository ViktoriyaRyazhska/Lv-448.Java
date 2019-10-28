package academy.softserve.museum.servlet.employee;

import academy.softserve.museum.constant.MessageType;
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

/**
 * Class processes requests for /employees/free-guides url.
 *
 * @version 1.0
 */
@WebServlet("/employees/free-guides")
public class FreeGuidesServlet extends HttpServlet {

    private EmployeeService employeeService;

    /**
     * Method initializes required resources.
     */
    @Override
    public void init() {
        employeeService = new EmployeeServiceImpl();
    }

    /**
     * Method processes POST request for /employees/free-guides url
     * and returns a list of free tour guides for given time range.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Date dateTimeFrom =
                    new Date(LocalDateTime.parse(req.getParameter("from"))
                            .toInstant(ZoneOffset.of("+03:00"))
                            .toEpochMilli());

            Date dateTimeTill =
                    new Date(LocalDateTime.parse(req.getParameter("till"))
                            .toInstant(ZoneOffset.of("+03:00"))
                            .toEpochMilli());

            List<Employee> freeGuides = employeeService.findAvailable(dateTimeFrom, dateTimeTill)
                    .stream()
                    .filter(e -> e.getPosition() == EmployeePosition.TOUR_GUIDE).collect(Collectors.toList());

            int size = freeGuides.size();

            req.setAttribute("employees", freeGuides);
            req.setAttribute(MessageType.SUCCESS, "There are " + size + " free guides");
        } catch (RuntimeException e) {
            req.setAttribute(MessageType.FAILURE, "Invalid date range");
            req.setAttribute("employees", employeeService.findAll());
        } finally {
            req.getRequestDispatcher("/employees.jsp").include(req, resp);
        }
    }

}
