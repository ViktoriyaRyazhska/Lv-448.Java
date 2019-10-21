package inc.softserve.services.implementations;

import inc.softserve.dao.implementations.*;
import inc.softserve.dao.interfaces.*;
import inc.softserve.database.ConnectDb;
import inc.softserve.entities.Hotel;
import inc.softserve.entities.Room;
import inc.softserve.entities.stats.RoomBooking;
import inc.softserve.services.intefaces.BookingService;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BookingStatsServiceImp implements BookingService {

    private BookingDao bookDao;
    private static RoomDao roomDao;
    private Connection conn;
    private BookingStatsServiceImp bookingService;

    public BookingStatsServiceImp(BookingDao bookDao, RoomDao roomDao, Connection conn) {
        this.bookDao = bookDao;
        this.roomDao = roomDao;
        this.conn = conn;
    }

    public BookingStatsServiceImp() {
        Connection conn = ConnectDb.connectBase();
        CityDao cityDao = new CityDaoJdbc(conn, new CountryDaoJdbc(conn));
        HotelDao hotelDao = new HotelDaoJdbc(conn, cityDao);
        roomDao = new RoomDaoJdbc(conn, hotelDao);
        bookDao = new BookingDaoJdbc(conn, null, roomDao, hotelDao);
        bookingService = new BookingStatsServiceImp(new BookingDaoJdbc(conn, null , roomDao, hotelDao), roomDao, conn);
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

    public Map<String, List<Room>> allFreeRoomsInCity(LocalDate now, LocalDate checkIn, LocalDate checkOut, Long cityId){
      //  Set<Room> allBookedRoom = roomDao.findAllFutureBookedRoomsByCityId(cityId);
        Set<Room> allBookedRoom = roomDao.findRoomsByCityId(cityId);
        List<RoomBooking> bookRooms = bookDao.findByRoomIdAndDate(1L, LocalDate.now());
        Set<RoomBooking> booking = freeRoom(bookRooms, checkIn, checkOut);
        Set<Room> bookingRoom = bookingRooms(booking);
      //  bookingRoom.removeAll(allBookedRoom);
        allBookedRoom.removeAll(bookingRoom);

      //  return allBookedRoom;
        return convertSetToMap(allBookedRoom);
    }

    private Map<String, List<Room>> convertSetToMap(Set<Room> allFreeRoomsInCity){
        Set<String> hotelName = new TreeSet<>();
        Map<String, List<Room>> sort = new HashMap<>();
        for (Room room : allFreeRoomsInCity){
            hotelName.add(room.getHotel().getHotelName());
        }
        for (String name : hotelName){
            List<Room> countRoom = new ArrayList<>();
            for (Room room : allFreeRoomsInCity){
                if (room.getHotel().getHotelName().equals(name)){
                    countRoom.add(room);
                }
            }
            sort.put(name, countRoom);

           // hotelName.add(name);
        }
        return sort;

    }

//    public static void main(String[] args) {
//        new BookingStatsServiceImp();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        Map<String, List<Room>> test= new BookingStatsServiceImp().allFreeRoomsInCity(LocalDate.now(), LocalDate.parse("2019/11/11", formatter),
//                LocalDate.parse("2019/12/11", formatter), 1L);
//
//
//     //   Set<Room> allBookedRoom = roomDao.findRoomsByCityId(1L);
//        System.out.println(test);
//    }

}
