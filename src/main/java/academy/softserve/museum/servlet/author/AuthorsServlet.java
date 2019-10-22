package academy.softserve.museum.servlet.author;

import academy.softserve.museum.services.AuthorService;
import academy.softserve.museum.services.impl.AuthorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authors")
public class AuthorsServlet extends HttpServlet {

    private AuthorService authorService;

    private static int authorsCounter = 2;

    @Override
    public void init() {
        authorService = new AuthorServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("authors", authorService.findAll());
        req.setAttribute("counter", authorsCounter);
        authorsCounter++;
        req.getRequestDispatcher("/fragment/author-dropdown.jsp").include(req, resp);
    }

    public static void dropCounter() {
        authorsCounter = 2;
    }
}
