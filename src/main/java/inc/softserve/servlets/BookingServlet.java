package inc.softserve.servlets;

import inc.softserve.dto.on_request.BookingReqDto;
import inc.softserve.entities.Usr;
import inc.softserve.exceptions.ContextParameterNotFound;
import inc.softserve.services.intefaces.BookingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(value = {"/booking/*"})
public class BookingServlet extends HttpServlet {

    private BookingService bookingService;

    @Override
    public void init() {
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
        if (bookingService == null){
            throw new ContextParameterNotFound();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LocalDate checkin = LocalDate.parse(req.getParameter("checkin"), DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                .plusDays(1); // MySQL's Date.valueOf(localDate) doesn't work correctly!!!
        LocalDate checkout = LocalDate.parse(req.getParameter("checkout"), DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                .plusDays(1); // MySQL's Date.valueOf(localDate) doesn't work correctly!!!
        Long usrId = ((Usr) req.getSession().getAttribute("user")).getId();
        Long roomId = Long.parseLong(req.getParameter("room_id"));
        Long hotelId = Long.parseLong(req.getParameter("hotel_id"));
        BookingReqDto bookingReqDto = BookingReqDto.builder()
                .checkin(checkin)
                .checkout(checkout)
                .usrId(usrId)
                .roomId(roomId)
                .hotelId(hotelId)
                .build();
        bookingService.book(bookingReqDto, LocalDate.now());
        req.setAttribute("message", "You have book a room");
    }
}
