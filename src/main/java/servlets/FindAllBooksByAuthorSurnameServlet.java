//package servlets;
//
//import entities.Book;
//import service.BookService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/find_all_books_by_author")
//public class FindAllBooksByAuthorSurnameServlet extends HttpServlet{
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        BookService bookService = new BookService();
//        String authorSurname = request.getParameter("authorSurname");
//        List<Book> books = bookService.findAllBooksByAuthorSurname(authorSurname);
//        request.setAttribute("listBookServices", books);
//        request.getRequestDispatcher("/test.jsp").forward(request, response);
//    }
//}
