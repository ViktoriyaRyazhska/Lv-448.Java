package servlets.Books;

import entities.Author;
import entities.Book;
import service.BookService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/add-book")
public class BooksCreateServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-book.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String releaseDate = req.getParameter("releaseDate");
        String amountOfInstances = req.getParameter("amountOfInstances");
        String surname = req.getParameter("surname");
        String firstName = req.getParameter("firstName");

        Author author = Author.builder().authorLastName(surname).authorFirstName(firstName).build();
        Book book = Book.builder().title(title)
                .releaseDate(LocalDate.parse(releaseDate))
                .amountOfInstances(Integer.parseInt(amountOfInstances))
                .author(author)
                .build();

        req.getRequestDispatcher("/add-author.jsp").include(req, resp);
    }
}

//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name");
//        String surname = req.getParameter("surname");
//        Author author = Author.builder().authorFirstName(name).authorLastName(surname).build();
//        try {
//            authorService.createAuthor(author);
//            resp.sendRedirect(req.getContextPath() + "/authors");
//        } catch (RuntimeException e) {
//            resp.sendRedirect(req.getContextPath() + "/authors");
//        }
//    }
//}
//
