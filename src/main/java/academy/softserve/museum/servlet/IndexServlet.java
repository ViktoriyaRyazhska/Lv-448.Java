package academy.softserve.museum.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for "/" (/index) url
 *
 * @version 1.0
 */
@WebServlet(value = {""})
public class IndexServlet extends HttpServlet {

    /**
     * Method processes GET request for /index url
     * and returns /index.jsp (homepage)
     *
     * @param req  HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").include(req, resp);
    }
}
