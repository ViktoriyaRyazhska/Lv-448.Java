package academy.softserve.museum.servlet.author;

import academy.softserve.museum.services.AuthorService;
import academy.softserve.museum.services.impl.AuthorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /authors url.
 * Used in /add-exhibit form to be able to add
 * multiple authors
 *
 * @version 1.0
 */
@WebServlet("/authors")
public class AuthorsServlet extends HttpServlet {

    private AuthorService authorService;


    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        authorService = new AuthorServiceImpl();
    }

    /**
     * Method processes GET request for /authors url
     * and returns /author-dropdown.jsp
     * for adding additional authors to new exhibit.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("authors", authorService.findAll());
        req.getRequestDispatcher("/fragment/author-dropdown.jsp").include(req, resp);
    }
}
