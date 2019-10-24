package servlets.author;

import service.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authors")
public class AuthorsServlet extends HttpServlet {
    private AuthorService authorService;

    @Override
    public void init() throws ServletException {
        authorService = new AuthorService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("authors", authorService.findAllAuthors());
        req.getRequestDispatcher("/authors.jsp").include(req, resp);
    }
}
