package servlets.books;

import entities.Author;
import entities.Book;
import service.AuthorService;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/add-book")
public class BooksCreateServlet extends HttpServlet {

    private BookService bookService;
    private AuthorService authorService;

    @Override
    public void init() {
        bookService = new BookService();
        authorService = new AuthorService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("authors", authorService.findAllAuthors());
        req.getRequestDispatcher("/add-book.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String releaseDate = req.getParameter("releaseDate");
        int amountOfInstances = Integer.parseInt(req.getParameter("amountOfInstances"));

        Long id = Long.parseLong(req.getParameter("author"));

        long co = Long.parseLong(req.getParameter("co-author"));
        System.out.println(co);

        Author author = authorService.findAuthorById(id);
        Book book = Book.builder().title(title)
                .releaseDate(LocalDate.parse(releaseDate))
                .amountOfInstances(amountOfInstances)
                .author(author)
                .build();
        try {
            bookService.createBook(book, author);
            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/books");
        }
    }
}
