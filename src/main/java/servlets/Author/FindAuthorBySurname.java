package servlets.Author;

import entities.Author;
import service.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/by_surname")
public class FindAuthorBySurname extends HttpServlet {

    private AuthorService authorService;

    @Override
    public void init() throws ServletException {
        authorService = new AuthorService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Author author = authorService.findAuthorBySurname(req.getParameter("surname"));
            req.setAttribute("author", authorService.findAuthorBySurname(author.getAuthorLastName()));
        } catch (IllegalArgumentException e) {
            req.setAttribute("failureMessage", e.getLocalizedMessage());
        }
        req.getRequestDispatcher("/authors.jsp").include(req, resp);

    }
}