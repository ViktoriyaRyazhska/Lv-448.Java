package servlets.books;

import database.DaoFactory;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/isAvailable")
public class IsAvailableServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String value = String.valueOf(DaoFactory.bookInstanceDao().isAvailable(Long.parseLong(id)));
        request.setAttribute("value", value);
        request.getRequestDispatcher("/test.jsp").forward(request, response);
    }
}
