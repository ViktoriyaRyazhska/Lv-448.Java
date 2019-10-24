package servlets.user;

import database.DaoFactory;
import entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/findUsersById")
public class FindUserByIdServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("userId");
        Optional<User> user = DaoFactory.userDao().findById(Long.parseLong(id));
        request.setAttribute("user", user);
        request.getRequestDispatcher("/test.jsp").forward(request, response);
    }
}
