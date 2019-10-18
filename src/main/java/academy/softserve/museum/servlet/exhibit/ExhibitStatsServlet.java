package academy.softserve.museum.servlet.exhibit;

import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.services.ExhibitService;
import academy.softserve.museum.services.impl.ExhibitServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet("/exhibit-statistics")
public class ExhibitStatsServlet extends HttpServlet {

    private ExhibitService exhibitService;

    @Override
    public void init() throws ServletException {
        exhibitService = new ExhibitServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        ExhibitStatistic statistic = exhibitService.findStatistic();

        String materialsJson = mapper.writeValueAsString(statistic.getMaterialStatistic().keySet());
        String materialsQuantityJson = mapper.writeValueAsString(statistic.getMaterialStatistic().values());

        String techniquesJson = mapper.writeValueAsString(statistic.getTechniqueStatistic().keySet());
        String techniquesQuantityJson = mapper.writeValueAsString(statistic.getTechniqueStatistic().values());

        req.setAttribute("materials", materialsJson);
        req.setAttribute("materialsQuantity", materialsQuantityJson);

        req.setAttribute("techniques", techniquesJson);
        req.setAttribute("techniquesQuantity", techniquesQuantityJson);

        req.getRequestDispatcher("/exhibit-chart.jsp").include(req, resp);
    }
}
