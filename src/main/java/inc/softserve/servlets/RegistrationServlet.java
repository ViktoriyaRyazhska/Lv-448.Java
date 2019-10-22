package inc.softserve.servlets;

import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.services.implementations.UsrRegisterImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UsrRegisterImpl usrRegistration;

    @Override
    public void init() {
        usrRegistration = new UsrRegisterImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsrRegisterImpl usrRegistration = new UsrRegisterImpl();
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String birthday = req.getParameter("date");
        String phoneNumber = req.getParameter("phone");
        String numberVisa = req.getParameter("numbervisa");
        String start = req.getParameter("start");
        String end = req.getParameter("end");
        String country = req.getParameter("country");
        UsrDto userDto = usrRegistration.initUsrDto(firstName, lastName, email, phoneNumber, birthday, password);
        VisaDto visaDto = usrRegistration.initVisaDto(country, start, end, numberVisa);
        usrRegistration.register(userDto, visaDto);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(req, resp);
    }
}
