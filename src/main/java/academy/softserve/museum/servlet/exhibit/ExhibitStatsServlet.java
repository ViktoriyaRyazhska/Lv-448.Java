package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;
import academy.softserve.museum.util.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class processes requests for /exhibits/statistics url.
 *
 * @version 1.0
 */
@WebServlet("/exhibits/statistics")
public class ExhibitStatsServlet extends HttpServlet {

    private ExhibitService exhibitService;

    /**
     * Method initializes required resources
     */
    @Override
    public void init() {
        exhibitService = new ExhibitServiceImpl();
    }

    /**
     * Method processes GET request for /exhibits/statistics url
     * and returns /exhibit-chart.jsp for visualization
     * of exhibit statistics.
     *
     * @param req HTTP request object
     * @param resp HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExhibitStatistic statistic = exhibitService.findStatistic();

        String materialsJson = Serializer.toJsonString(statistic.getMaterialStatistic().keySet());
        String materialsQuantityJson = Serializer.toJsonString(statistic.getMaterialStatistic().values());

        String techniquesJson = Serializer.toJsonString(statistic.getTechniqueStatistic().keySet());
        String techniquesQuantityJson = Serializer.toJsonString(statistic.getTechniqueStatistic().values());

        req.setAttribute("materials", materialsJson);
        req.setAttribute("materialsQuantity", materialsQuantityJson);

        req.setAttribute("techniques", techniquesJson);
        req.setAttribute("techniquesQuantity", techniquesQuantityJson);

        req.getRequestDispatcher("/exhibit-chart.jsp").include(req, resp);
    }
}
