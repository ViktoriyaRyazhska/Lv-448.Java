package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.constant.MessageType;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import academy.softserve.museum.entities.dto.ExhibitDto;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.services.AudienceService;
import academy.softserve.museum.services.AuthorService;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.AudienceServiceImpl;
import academy.softserve.museum.services.impl.AuthorServiceImpl;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;
import academy.softserve.museum.servlet.author.AuthorsServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class processes requests for /exhibits/add-exhibit url.
 *
 * @version 1.0
 */
@WebServlet("/exhibits/add-exhibit")
public class AddExhibitServlet extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;
    private AuthorService authorService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
        authorService = new AuthorServiceImpl();
    }

    /**
     * Method processes GET request for /exhibits/add-exhibit url
     * and returns /add-exhibit.jsp form
     * for adding new exhibits.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorsServlet.dropCounter();
        req.setAttribute("authors", authorService.findAll());
        req.setAttribute("types", exhibitService.getTypes());
        req.setAttribute("audiences", audienceService.findAll());
        req.getRequestDispatcher("/add-exhibit.jsp").include(req,resp);
    }

    /**
     * Method processes POST request for /exhibits/add-exhibit url
     * gets parameters from request object,
     * creates new Exhibit object and passes it to service layer.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String material = req.getParameter("material");
        String technic = req.getParameter("technic");
        ExhibitType exhibitType = ExhibitType.valueOf(req.getParameter("type"));
        Long audienceId = Long.parseLong(req.getParameter("audience"));
        List<Long> authors = new ArrayList<>();

        Map<String, String[]> parameterMap = req.getParameterMap();
        for(String key: parameterMap.keySet()){
            if(key.matches("^author-([0-9]{1,3})$")) {
                authors.add(Long.parseLong(req.getParameter(key)));
            }
        }

        ExhibitDto exhibitDto = new ExhibitDto(exhibitType, name, material, technic, audienceId, authors);

        try {
            exhibitService.save(exhibitDto);
            req.setAttribute(MessageType.SUCCESS, "ALES GUT!");
            resp.sendRedirect(req.getContextPath() + "/exhibits");
        } catch (NotSavedException e) {
            req.setAttribute(MessageType.FAILURE, e.getMessage());
            req.getRequestDispatcher("/add-exhibit.jsp").forward(req, resp);
        }

    }
}
