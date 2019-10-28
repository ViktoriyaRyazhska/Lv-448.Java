package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.constant.MessageType;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.AuthorService;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.AuthorServiceImpl;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /exhibits/by-author url.
 *
 * @version 1.0
 */
@WebServlet("/exhibits/by-author")
public class ExhibitsByAuthor extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;
    private AuthorService authorService;

    /**
     * Method initializes required resources.
     */
    @Override
    public void init() {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
        authorService = new AuthorServiceImpl();
    }

    /**
     * Method processes POST request for /exhibits/by-author url
     * and returns list of exhibits by given author.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Author author = authorService.findByFullName(req.getParameter("firstName"), req.getParameter("lastName")).get();
            req.setAttribute("exhibits", exhibitService.findByAuthor(author));
            req.setAttribute(MessageType.SUCCESS, "Found " + exhibitService.findByAuthor(author).size() + " result(s)");
        } catch (RuntimeException e) {
            req.setAttribute(MessageType.FAILURE, "Author does not exist");
            req.setAttribute("exhibits", exhibitService.findAll());
        } finally {
            req.setAttribute("audiences", audienceService.findAll());
            req.getRequestDispatcher("/exhibits.jsp").include(req,resp);
        }
    }
}
