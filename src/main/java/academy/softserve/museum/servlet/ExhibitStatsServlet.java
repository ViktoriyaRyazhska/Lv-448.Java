package academy.softserve.museum.servlet;

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

@WebServlet("/exhibit-statistics")
public class ExhibitStatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        List materials = new ArrayList<>(Arrays.asList("Alebastr", "Мarble", "Clay"));
        List materialsQuantity = new ArrayList<>(Arrays.asList(4, 5, 6));

        String materialsJson = mapper.writeValueAsString(materials);
        String materialsQuantityJson = mapper.writeValueAsString(materialsQuantity);

        req.setAttribute("materials", materialsJson);
        req.setAttribute("materialsQuantity", materialsQuantityJson);

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/exhibit-chart.jsp");
        requestDispatcher.forward(req, resp);
    }
}
