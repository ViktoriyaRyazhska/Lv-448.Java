package servlets.Author;

import entities.Author;
import service.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-author")
public class AuthorCreateServlet extends HttpServlet {
    private AuthorService authorService;

    @Override
    public void init() throws ServletException {
        authorService = new AuthorService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-author.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        Author author = Author.builder().authorFirstName(name).authorLastName(surname).build();
        try {
            authorService.createAuthor(author);
            resp.sendRedirect(req.getContextPath() + "/authors");
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/authors");
        }
    }
}
