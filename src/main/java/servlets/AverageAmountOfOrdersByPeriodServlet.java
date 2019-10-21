/*package servlets;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/avgAmountOfOrdersByPeriod")
public class AverageAmountOfOrdersByPeriodServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        Date fromDate = request.getParameter(); //request.get не працює з датою, треба вхідну стрінгу
        Date toDate = request.getParameter();
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        String value = String.valueOf(userService.averageAmountOfOrdersBySomePeriod(fromDate, toDate));
        request.setAttribute("value", value);
        request.getRequestDispatcher("/test.jsp").forward(request, response);
    }
}*/
