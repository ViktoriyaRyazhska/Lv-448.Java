package inc.softserve.servlets;

import inc.softserve.dao.implementations.*;
import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.database.ConnectDb;
import inc.softserve.entities.Room;
import inc.softserve.entities.stats.RoomBooking;
import inc.softserve.services.implementations.BookingStatsServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//@WebServlet(value = {"/booking"})
@WebServlet(name = "BookingServlet",
        urlPatterns = "/booking")
public class BookingServlet extends HttpServlet {

    private BookingStatsServiceImp bookingService;

    @Override
    public void init() {
        Connection conn = ConnectDb.connectBase();
        CityDao cityDao = new CityDaoJdbc(conn, new CountryDaoJdbc(conn));
        HotelDao hotelDao = new HotelDaoJdbc(conn, cityDao);
        RoomDao roomDao = new RoomDaoJdbc(conn, hotelDao);
      //  BookingDao bookDao = new BookingDaoJdbc(conn, null, roomDao, hotelDao);
        bookingService = new BookingStatsServiceImp(new BookingDaoJdbc(conn, null , roomDao, hotelDao), roomDao, conn);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      //  Set<RoomBooking> bookings;
       String bookings = bookingService.allFreeRoomsInCity(LocalDate.now(), LocalDate.parse("2019/11/11", formatter),
                LocalDate.parse("2019/12/11", formatter), 1L);;

        req.setAttribute("bookings", bookings);
      //  req.setAttribute("text", "WORK");
        req.getRequestDispatcher("/booking.jsp")
                .include(req, resp);


    }

}
