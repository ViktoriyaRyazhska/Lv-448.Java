package inc.softserve.servlets;

import inc.softserve.dto.RoomDto;
import inc.softserve.exceptions.ContextParameterNotFound;
import inc.softserve.services.intefaces.RoomService;
import inc.softserve.utils.mappers.ObjectToJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@WebServlet(value = {"/rooms"})
public class RoomServlet extends HttpServlet {

    private RoomService roomService;

    @Override
    public void init() {
        roomService = (RoomService) getServletContext().getAttribute("roomService");
        if (roomService == null){
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
}
