package servlets.order;

import entities.BookInstance;
import entities.Order;
import entities.User;
import service.BookInstanceService;
import service.BookService;
import service.OrderService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-order")
public class OrderCreateServlet extends HttpServlet {
    private OrderService orderService;
    private UserService userService;
    private BookService bookService;
    private BookInstanceService bookInstanceService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        userService = new UserService();
        bookInstanceService = new BookInstanceService();
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.findAllBook());
        req.setAttribute("users", userService.findAll());
        req.getRequestDispatcher("/add-order.jsp").include(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idUser = Long.parseLong(req.getParameter("user"));
        Long idBookInstance = Long.parseLong(req.getParameter("bookInstance"));


        User user = userService.findUserById(idUser);
        BookInstance bookInstance = bookInstanceService.findById(idBookInstance);

        Order order = Order.builder()
                .user(user)
                .bookInstance(bookInstance)
                .build();
        bookInstance.setIsAvailable(false);

        try {
            orderService.createOrder(order);
            bookInstanceService.setAvailableBookInstance(idBookInstance, false);
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
        }
    }
}
