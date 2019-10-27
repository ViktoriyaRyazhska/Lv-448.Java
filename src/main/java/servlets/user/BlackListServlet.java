package servlets.user;

import database.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@WebServlet("/blacklist")
public class BlackListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map users = DaoFactory.userDao().geBlackList();
        request.setAttribute("user", users);
        request.getRequestDispatcher("/blacklist.jsp").forward(request, response);
    }
}
