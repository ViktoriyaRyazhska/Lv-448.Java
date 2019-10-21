package servlets;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/timeOfUsingLibraryByUser")
public class TimeOfUsingLibraryByUserServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            UserService userServices = new UserService();
            String id = request.getParameter("id");
            String value = String.valueOf(userServices.timeOfUsingLibraryByUser(Long.parseLong(id)));
            request.setAttribute("value", value);
            request.getRequestDispatcher("/test.jsp").forward(request, response);
        }
}


