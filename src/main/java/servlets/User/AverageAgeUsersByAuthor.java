//package servlets.User;
//
//import service.UserService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/age-users-by-author")
//public class AverageAgeUsersByAuthor extends HttpServlet {
//    private UserService userService;
//
//    @Override
//    public void init() throws ServletException {
//        userService = new UserService();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute();
//        Integer integer = userService.averageAgeUsersByAuthor();
//        req.getRequestDispatcher("/authors.jsp").include(req, resp);
//    }
//}
