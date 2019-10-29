package inc.softserve.servlets;

import inc.softserve.entities.Usr;
import inc.softserve.services.implementations.UsrRegisterImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
/**
 * Class processes requests for (/login) url
 */
@WebServlet(value = {"/login"})
public class LoginServlet extends HttpServlet {

    private UsrRegisterImpl userService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        this.userService = (UsrRegisterImpl) getServletContext().getAttribute("usrRegisterService");
    }

    /**
     * Method processes GET request for /login url
     * and returns /login.jsp
     *
     * @param request  HTTP request object
     * @param response HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Method processes POST request for /login url
     *
     * @param request HTTP request object
     * @param response HTTP response object
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Map<String, String> messages = userService.validateData(email, password);
        HttpSession session=request.getSession();
        if (messages.isEmpty()) {
            Usr user = null;
            try {
                user = userService.login(email, password);
            } catch (RuntimeException e) {
                messages.put("email", "Entered email or Password incorrect!");
                request.setAttribute("messages", messages);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index");
            } else {
                messages.put("email", "Entered email or Password incorrect!");
                request.setAttribute("messages", messages);
                request.getRequestDispatcher("/login.jsp").forward(request, response);

            }
        } else {
            request.setAttribute("messages", messages);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
