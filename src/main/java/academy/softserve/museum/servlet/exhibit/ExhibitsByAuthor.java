package academy.softserve.museum.servlet.exhibit;

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


@WebServlet("/exhibits-by-author")
public class ExhibitsByAuthor extends HttpServlet {

    private ExhibitService exhibitService;
    private AudienceService audienceService;
    private AuthorService authorService;

    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
        audienceService = new AudienceServiceImpl();
        authorService = new AuthorServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Author author = authorService.findByFullName(req.getParameter("firstName"), req.getParameter("lastName")).get();
        req.setAttribute("audiences", audienceService.findAll());
        req.setAttribute("exhibits", exhibitService.findByAuthor(author));
        req.getRequestDispatcher("/exhibits.jsp").include(req,resp);
    }
}
