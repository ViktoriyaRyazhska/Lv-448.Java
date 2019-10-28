package inc.softserve.cfg;

import inc.softserve.dao.implementations.*;
import inc.softserve.dao.interfaces.*;
import inc.softserve.connectivity.ConnectDb;
import inc.softserve.security.JavaNativeSaltGen;
import inc.softserve.security.SaltGen;
import inc.softserve.services.implementations.*;
import inc.softserve.services.intefaces.*;
import lombok.Getter;

import java.sql.Connection;

/**
 * This is a 'hand-made' container for inversion of control since only tomcat EE has IoC
 */
@Getter
class ContextContainer {

    static Connection connection = ConnectDb.connectBase();

    static UsrCountryDao usrCountryDao = new UsrCountryDaoJdbc(connection);
    static CountryDao countryDao = new CountryDaoJdbc(connection);
    static UsrDao usrDao = new UsrDaoJdbc(connection);
    static VisaDao visaDao = new VisaDaoJdbc(connection, usrDao, countryDao);
    static CityDao cityDao = new CityDaoJdbc(connection, countryDao);
    static HotelDao hotelDao = new HotelDaoJdbc(connection, cityDao);
    static RoomDao roomDao = new RoomDaoJdbc(connection, hotelDao);
    static BookingDao bookingDao = new BookingDaoJdbc(connection, usrDao, roomDao, hotelDao);

    static CountryService countryService = new CountryServiceImpl(countryDao, cityDao);
    static HotelService hotelService = new HotelServiceImpl(bookingDao, roomDao, hotelDao, cityDao, countryDao);
    static RoomService roomService = new RoomServiceImpl(roomDao, bookingDao);
    static VisaStatsService visaStatsService = new VisaStatsServiceImpl(visaDao, usrDao, countryDao);
    static HotelStatsService hotelStatsService = new HotelStatsServiceImpl(hotelDao, roomDao);
    static SaltGen saltGen = new JavaNativeSaltGen();
    static UsrRegisterService usrRegisterService= new UsrRegisterImpl(saltGen, usrDao, visaDao, countryDao, connection);
    static BookingService bookingService = new BookingServiceImpl(bookingDao, usrDao, hotelDao, roomDao, usrCountryDao);
    static RoomStatsService roomStatsService = new RoomStatsServiceImpl(roomDao);
}
