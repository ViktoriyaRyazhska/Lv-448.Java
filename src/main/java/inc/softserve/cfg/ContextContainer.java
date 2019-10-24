package inc.softserve.cfg;

import inc.softserve.dao.implementations.*;
import inc.softserve.dao.interfaces.*;
import inc.softserve.database.ConnectDb;
import inc.softserve.security.JavaNativeSaltGen;
import inc.softserve.security.SaltGen;
import inc.softserve.services.implementations.*;
import inc.softserve.services.intefaces.*;
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
    static VisaStatsService visaStatsService = new VisaStatsServiceImpl(visaDao, usrDao, countryDao);
    static HotelStatsService hotelStatsService = new HotelStatsServiceImpl(hotelDao, roomDao);
    static SaltGen saltGen = new JavaNativeSaltGen();
    static UsrRegisterService usrRegisterService= new UsrRegisterImpl(saltGen, usrDao, visaDao, countryDao, connection);
    static BookingService bookingService = new BookingServiceImpl(bookingDao, usrDao, hotelDao, roomDao);
    static RoomStatsService roomStatsService = new RoomStatsServiceImpl(roomDao);
}
