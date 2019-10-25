package servlets.order;

import service.BookInstanceService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orderClose")
public class OrderCloseServlet extends HttpServlet {
    private OrderService orderService;
    private BookInstanceService bookInstanceService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        bookInstanceService = new BookInstanceService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("idOrder", orderService.closeOrder(Long.parseLong(req.getParameter("close"))));
            bookInstanceService.setAvailableBookInstance(orderService.findById(Long.parseLong(req.getParameter("close"))).getId(),true);
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
        }
    }
}
