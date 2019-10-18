package inc.softserve.services.implementations;

import inc.softserve.dao.implementations.BookingDaoJdbc;
import inc.softserve.dao.implementations.HotelDaoJdbc;
import inc.softserve.dao.implementations.RoomDaoJdbc;
import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.database.ConnectDb;
import inc.softserve.entities.Hotel;
import inc.softserve.entities.Room;
import inc.softserve.services.intefaces.BookingService;

import java.sql.Connection;
import java.util.*;

public class BookingStatsServiceImp implements BookingService {

    private BookingDao bookDao;
    private HotelDao hotelDao;
    private static RoomDao roomDao;
    private Connection conn;

    public BookingStatsServiceImp(BookingDaoJdbc bookDao, HotelDaoJdbc hotelDao) {
        this.bookDao = bookDao;
        this.hotelDao = hotelDao;
        conn = ConnectDb.connectBase();
    }

    public BookingStatsServiceImp() {
        hotelDao = new HotelDaoJdbc(ConnectDb.connectBase(), null);
        conn = ConnectDb.connectBase();
        roomDao = new RoomDaoJdbc(conn, hotelDao);
    }

    public Set<Hotel> allRoomByHotel(Long cityId) {

        return hotelDao.findHotelsByCityId(cityId);
    }

    private static Set<Room> allRoomInCity(Long hotelId ){
        return roomDao.findByHotelId(hotelId);
    }

    public static void main(String[] args) {
    }
}
