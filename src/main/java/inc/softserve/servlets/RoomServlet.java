package inc.softserve.servlets;

import inc.softserve.dto.RoomDto;
import inc.softserve.dto.on_request.BookingReqDto;
import inc.softserve.entities.Usr;
import inc.softserve.exceptions.ContextParameterNotFound;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.BookingService;
import inc.softserve.services.intefaces.RoomService;
import inc.softserve.utils.mappers.ObjectToJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@WebServlet(value = {"/rooms"})
public class RoomServlet extends HttpServlet {

    private RoomService roomService;
    private BookingService bookingService;

    @Override
    public void init() {
        roomService = (RoomService) getServletContext().getAttribute("roomService");
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
        if (roomService == null || bookingService == null){
            throw new ContextParameterNotFound();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long hotelId = Long.parseLong(req.getParameter("hotelId"));
        Set<RoomDto> roomsPojo = roomService.findRoomsAndTheirBookingsStartingFrom(hotelId, LocalDate.now());
        req.setAttribute("roomsPojo", roomsPojo);
        req.setAttribute("roomsJson", ObjectToJson.map(roomsPojo));
        req.getRequestDispatcher("/rooms.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        try {
            bookingService.book(bookingReqDto, LocalDate.now());
            req.setAttribute("message", "You have book a room");
            req.getRequestDispatcher("booking.jsp").include(req, resp);
        } catch (InvalidTimePeriod e){
            req.setAttribute("message", "Invalid time period");
            req.getRequestDispatcher("booking.jsp").include(req, resp);
        }
    }
}
