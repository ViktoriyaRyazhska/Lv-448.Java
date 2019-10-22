package inc.softserve.servlets;

import inc.softserve.entities.Usr;
import inc.softserve.services.implementations.UsrRegisterImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(value = {"/login"})
public class LoginServlet extends HttpServlet {
    private UsrRegisterImpl userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UsrRegisterImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Map<String, String> messages = userService.validateData(email, password);
        HttpSession session=request.getSession();
        if (messages.isEmpty()) {
            Usr user = userService.LoginIn(email, password);
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index");
            } else {
                messages.put("login", "Unknown login, please try again");
            }
        }
    }

}
