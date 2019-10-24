package servlets.books;

import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/books")
public class BooksServlet extends HttpServlet {

    BookService bookService;

    @Override
    public void init() {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.findAllBook());
        req.getRequestDispatcher("/books.jsp").include(req, resp);
    }
}
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("authors", authorService.findAllAuthors());
//        req.getRequestDispatcher("/authors.jsp").include(req, resp);
//    }
//}
