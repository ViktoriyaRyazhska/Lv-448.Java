package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exhibits-by-audience")
public class ExhibitsByAudience extends HttpServlet {

    private ExhibitService exhibitService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        exhibitService = new ExhibitServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
