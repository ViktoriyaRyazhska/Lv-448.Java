package inc.softserve.cfg;

import inc.softserve.dao.implementations.*;
import inc.softserve.dao.interfaces.*;
import inc.softserve.database.ConnectDb;
import inc.softserve.services.implementations.CountryServiceImpl;
import inc.softserve.services.implementations.HotelServiceImpl;
import inc.softserve.services.implementations.RoomServiceImpl;
import inc.softserve.services.intefaces.CountryService;
import inc.softserve.services.intefaces.HotelService;
import inc.softserve.services.intefaces.RoomService;
import lombok.Getter;

import java.sql.Connection;

@Getter
class ContextContainer {

    static Connection connection = ConnectDb.connectBase();

    static CountryDao countryDao = new CountryDaoJdbc(connection);
    static UsrDao usrDao = new UsrDaoJdbc(connection);
    static VisaDao visaDao = new VisaDaoJdbc(connection, usrDao, countryDao);
    static CityDao cityDao = new CityDaoJdbc(connection, countryDao);
    static HotelDao hotelDao = new HotelDaoJdbc(connection, cityDao);
    static RoomDao roomDao = new RoomDaoJdbc(connection, hotelDao);
    static BookingDao bookingDao = new BookingDaoJdbc(connection, usrDao, roomDao, hotelDao);

    static CountryService countryService = new CountryServiceImpl(countryDao, cityDao);
    static HotelService hotelService = new HotelServiceImpl(bookingDao, hotelDao, cityDao, countryDao);
    static RoomService roomService = new RoomServiceImpl(roomDao, bookingDao);
}
