package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dto.on_request.BookingReqDto;
import inc.softserve.entities.Booking;
import inc.softserve.exceptions.InvalidTimePeriod;
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
    public void book(BookingReqDto bookingReqDto, LocalDate orderDate){
        LocalDate checkin = bookingReqDto.getCheckin();
        LocalDate checkout = bookingReqDto.getCheckout();
        if (checkin.compareTo(checkout) > 0){
            throw new InvalidTimePeriod();
        }
        bookingDao.save(setBooking(bookingReqDto, orderDate));
    }

    private Booking setBooking(BookingReqDto bookingReqDto, LocalDate orderDate){
        Booking booking = new Booking();
        booking.setUsr(usrDao.findById(bookingReqDto.getUsrId())
                .orElseThrow());
        booking.setHotel(hotelDao.findById(bookingReqDto.getHotelId())
                .orElseThrow());
        booking.setRoom(roomDao.findById(bookingReqDto.getRoomId())
                .orElseThrow());
        booking.setOrderDate(orderDate);
        booking.setCheckin(bookingReqDto.getCheckin());
        booking.setCheckout(bookingReqDto.getCheckout());
        return booking;
    }
}
