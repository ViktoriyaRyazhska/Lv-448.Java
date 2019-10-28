package academy.softserve.museum.servlet.author;

import academy.softserve.museum.entities.dto.AuthorDto;
import academy.softserve.museum.services.AuthorService;
import academy.softserve.museum.services.impl.AuthorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /exhibits/add-author url.
 *
 * @version 1.0
 */
@WebServlet("/exhibits/add-author")
public class AddAuthorServlet extends HttpServlet {

    private AuthorService authorService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        authorService = new AuthorServiceImpl();
    }

    /**
     * Method processes GET request for /exhibits/add-author url
     * and returns /add-author.jsp form
     * for adding new authors.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-author.jsp").include(req, resp);
    }

    /**
     * Method processes POST request for /exhibits/add-author url
     * gets parameters from request object,
     * creates new AuthorDto and passes it to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String firstname = req.getParameter("firstname");
            String lastname = req.getParameter("lastname");
            AuthorDto authorDto = new AuthorDto(firstname, lastname);
            authorService.save(authorDto);
            resp.sendRedirect(req.getContextPath() + "/exhibits");
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/exhibits");
        }
    }
}
