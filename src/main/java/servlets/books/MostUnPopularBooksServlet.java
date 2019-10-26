package servlets.books;

import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/most-unpopular-books")
public class MostUnPopularBooksServlet extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromDate = req.getParameter("fromDate");
        String toDate = req.getParameter("toDate");


        req.setAttribute("map", bookService.mostUnPopularBookBetweenDate(LocalDate.parse(fromDate),
                LocalDate.parse(toDate)));

        req.getRequestDispatcher("/most-popular-books.jsp").include(req, resp);
    }
}
