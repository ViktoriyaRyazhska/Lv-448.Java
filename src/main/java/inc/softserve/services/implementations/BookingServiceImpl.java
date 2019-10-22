package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dto.BookingDto;
import inc.softserve.entities.Booking;
import inc.softserve.services.intefaces.BookingService;

import java.time.LocalDate;

public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final UsrDao usrDao;
    private final HotelDao hotelDao;
    private final RoomDao roomDao;


    public BookingServiceImpl(BookingDao bookingDao, UsrDao usrDao, HotelDao hotelDao, RoomDao roomDao) {
        this.bookingDao = bookingDao;
        this.usrDao = usrDao;
        this.hotelDao = hotelDao;
        this.roomDao = roomDao;
    }

    @Override
    public void book(BookingDto bookingDto, LocalDate orderDate){
        bookingDao.save(mapToBooking(bookingDto, orderDate));
    }

    private Booking mapToBooking(BookingDto bookingDto, LocalDate orderDate){
        Booking booking = new Booking();
        booking.setUsr(usrDao.findById(bookingDto.getUsrId())
                .orElseThrow());
        booking.setHotel(hotelDao.findById(bookingDto.getHotelId())
                .orElseThrow());
        booking.setRoom(roomDao.findById(bookingDto.getRoomId())
                .orElseThrow());
        booking.setOrderDate(orderDate);
        booking.setCheckin(bookingDto.getCheckin());
        booking.setCheckout(bookingDto.getCheckout());
        return booking;
    }
}
