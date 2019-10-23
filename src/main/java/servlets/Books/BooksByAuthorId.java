package servlets.Books;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/books-by-author-id/*")
public class BooksByAuthorId extends HttpServlet {
}
