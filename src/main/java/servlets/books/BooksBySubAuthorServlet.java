package servlets.books;

import service.BookService;
import utils.PathParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/books-by-sub-author-id/*")
public class BooksBySubAuthorServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = PathParser.getPathVariable(req.getPathInfo());
        req.setAttribute("books", bookService.findAllBooksBySubAuthorId(id));
        req.getRequestDispatcher("/books.jsp").include(req, resp);
    }
}
