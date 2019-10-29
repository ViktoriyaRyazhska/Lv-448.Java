package inc.softserve.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    /**
     * Method initializes required resources
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Method check sing in user
     *
     * @param servletRequest id Hotel entity
     * @param servletResponse from date
     *
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        if (loggedIn) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(response.encodeRedirectURL("/login.jsp"));
        }
    }

    @Override
    public void destroy() {
    }
}
