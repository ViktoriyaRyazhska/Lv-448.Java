package inc.softserve.servlets;

import inc.softserve.entities.Usr;
import inc.softserve.services.UsrRegisterImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

//    private UsrRegisterImpl userService;
//
//    public LoginServlet(UsrRegisterImpl userService) {
//        this.userService = userService;
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsrRegisterImpl userService = new UsrRegisterImpl();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Map<String, String> messages = userService.validateData(email, password);
        HttpSession session=request.getSession();
        if (messages.isEmpty()) {
            Usr user = userService.LoginIn(email, password);
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                messages.put("login", "Unknown login, please try again");
            }
        }
    }

}
