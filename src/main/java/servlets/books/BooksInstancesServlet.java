package servlets.books;

import dto.BookInstanceDto;
import entities.BookInstance;
import service.BookInstanceService;
import utils.PathParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/books-instances/*")
public class BooksInstancesServlet extends HttpServlet {

    private BookInstanceService bookInstanceService;

    @Override
    public void init() throws ServletException {
        bookInstanceService = new BookInstanceService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = PathParser.getPathVariable(req.getPathInfo());
        List<BookInstanceDto> instances = bookInstanceService
                        .findAllBookInstanceByBookId(id)
                .stream().filter(BookInstanceDto::getIsAvailable).collect(Collectors.toList());
        req.setAttribute("instances", instances);
        req.getRequestDispatcher("/book-instances.jsp").include(req, resp);
    }
}
