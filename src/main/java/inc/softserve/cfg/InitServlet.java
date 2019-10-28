package inc.softserve.cfg;

import inc.softserve.connectivity.ConnectDb;
import inc.softserve.utils.rethrowing_lambdas.RethrowingLambdas;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Stream;

/**
 * A servlet that loads first and puts all components (beans) in the servlet context.
 */
@WebServlet(value = {"/config_servlet"}, loadOnStartup = Integer.MAX_VALUE)
public class InitServlet extends HttpServlet {

    @Override
    public void init() {
        onStartup();
    }

    private void onStartup() {
        final ServletContext ctx = getServletContext();
        Stream.of(ContextContainer.class.getDeclaredFields())
                .map(RethrowingLambdas.function(
                        field -> Map.entry(field.getName(), field.get(null))
                ))
                .forEach(entry -> ctx.setAttribute(entry.getKey(), entry.getValue()));
    }

    @Override
    public void destroy() {
        try {
            ConnectDb.connectBase().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
