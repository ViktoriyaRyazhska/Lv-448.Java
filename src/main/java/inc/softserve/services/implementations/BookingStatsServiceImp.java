package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.entities.Room;
import inc.softserve.entities.stats.RoomBooking;
import inc.softserve.services.intefaces.BookingService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BookingStatsServiceImp implements BookingService {

    private BookingDao bookDao;
    private RoomDao roomDao;
    private Connection conn;

    public BookingStatsServiceImp(BookingDao bookDao, RoomDao roomDao, Connection conn) {
        this.bookDao = bookDao;
        this.roomDao = roomDao;
        this.conn = conn;
    }

    private boolean overlaps(RoomBooking roomBooking, LocalDate checkin, LocalDate checkout){
        return (roomBooking.getCheckin().compareTo(checkout) < 0)
                &&
                (roomBooking.getCheckout().compareTo(checkin) > 0);
    }

    private Set<RoomBooking> freeRoom(List<RoomBooking> bookRooms, LocalDate checkIn, LocalDate checkOut){
        return bookRooms.stream()
                 .filter(b -> overlaps(b, checkIn, checkOut))
                 .collect(Collectors.toSet());
    }

    private Set<Room> bookingRooms(Set<RoomBooking> booking){
        Set<Room> rooms = new HashSet<>();
        for(RoomBooking room : booking){
            rooms.add(roomDao.findById(room.getRoom().getId()).orElseThrow());
        }
        return rooms;
    }

    public Set<RoomBooking> allFreeRoomsInCity(LocalDate now, LocalDate checkIn, LocalDate checkOut, Long cityId){
        Set<Room> allBookedRoom = roomDao.findAllFutureBookedRoomsByCityId(cityId);
        List<RoomBooking> bookRooms = bookDao.findByRoomIdAndDate(1L, LocalDate.now());
        Set<RoomBooking> booking = freeRoom(bookRooms, checkIn, checkOut);
        Set<Room> bookingRoom = bookingRooms(booking);
        bookingRoom.removeAll(allBookedRoom);
        return booking;
    }

}
