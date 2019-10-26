package servlets.books;

import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/most-popular-books")
public class MostPopularBooksServlet extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromDate = req.getParameter("fromDate");
        String toDate = req.getParameter("toDate");

        int size = bookService.mostPopularBookBetweenDate(LocalDate.parse(fromDate),
                LocalDate.parse(toDate)).size();


        req.setAttribute("books", bookService.mostPopularBookBetweenDate(LocalDate.parse(fromDate),
                LocalDate.parse(toDate)));

        req.getRequestDispatcher("/books.jsp").include(req, resp);
    }
}
