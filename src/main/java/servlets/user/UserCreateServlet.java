package servlets.user;

import entities.Address;
import entities.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/add-user")
public class UserCreateServlet extends HttpServlet{
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-user.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String birthday = req.getParameter("birthday");
        String registrationDate = req.getParameter("registrationDate");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        String buildingNumber = req.getParameter("buildingNumber");
        String apartment = req.getParameter("apartment");
        Address address = Address.builder()
                .city(city)
                .street(street)
                .buildingNumber(Long.parseLong(buildingNumber))
                .apartment(Long.parseLong(apartment))
                .build();

        User user = User.builder()
                .userName(name)
                .userSurname(surname)
                .birthday(LocalDate.parse(birthday))
                .registrationDate(LocalDate.parse(registrationDate))
                .phoneNumber(phoneNumber)
                .email(email)
                .userAddress(address)
                .build();
        try {
            userService.createUser(user, address);
            resp.sendRedirect(req.getContextPath() + "/users");
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/users");
        }
    }
}
