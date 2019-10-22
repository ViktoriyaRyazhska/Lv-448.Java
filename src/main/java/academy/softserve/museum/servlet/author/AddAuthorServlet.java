package academy.softserve.museum.servlet.author;

import academy.softserve.museum.constant.MessageType;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.services.AuthorService;
import academy.softserve.museum.services.impl.AuthorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exhibits/add-author")
public class AddAuthorServlet extends HttpServlet {

    private AuthorService authorService;

    @Override
    public void init() throws ServletException {
        authorService = new AuthorServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-author.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String firstname = req.getParameter("firstname");
            String lastname = req.getParameter("lastname");
            Author author = new Author(firstname, lastname);
            authorService.save(author);
//            req.getRequestDispatcher("/exhibits").include(req, resp);
            resp.sendRedirect(req.getContextPath() + "/exhibits");
        } catch (RuntimeException e) {
//            req.setAttribute(MessageType.FAILURE, "Something went wrong");
//            req.getRequestDispatcher("/exhibits").include(req, resp);
            resp.sendRedirect(req.getContextPath() + "/exhibits");
        }
    }
}
