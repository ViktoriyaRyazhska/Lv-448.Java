package inc.softserve.servlets;

import inc.softserve.dto.BookingDto;
import inc.softserve.entities.Booking;
import inc.softserve.entities.Usr;
import inc.softserve.services.intefaces.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = {"/booking/*"})
public class BookingServlet extends HttpServlet {

    private BookingService bookingService;

    @Override
    public void init() {
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        LocalDate checkin = LocalDate.parse(req.getParameter("checkin"), DateTimeFormatter.ofPattern("MM/dd/YYYY"));
//        LocalDate checkout = LocalDate.parse(req.getParameter("checkout"), DateTimeFormatter.ofPattern("MM/dd/YYYY"));
        LocalDate checkin = LocalDate.of(2019, 10, 21);
        LocalDate checkout = LocalDate.of(2019, 10, 31);
        Long usrId = ((Usr) req.getSession().getAttribute("user")).getId();
        Long roomId = Long.parseLong(req.getParameter("room_id"));
        Long hotelId = Long.parseLong(req.getParameter("hotel_id"));
        BookingDto bookingDto = BookingDto.builder()
                .checkin(checkin)
                .checkout(checkout)
                .usrId(usrId)
                .roomId(roomId)
                .hotelId(hotelId)
                .build();
        bookingService.book(bookingDto, LocalDate.now());
        req.setAttribute("message", "You have book a room");
    }

}
