package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.*;
import inc.softserve.dto.on_request.BookingReqDto;
import inc.softserve.entities.Booking;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.BookingService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final UsrDao usrDao;
    private final HotelDao hotelDao;
    private final RoomDao roomDao;
    private final UsrCountryDao usrCountryDao;

    /**
     * Constructor with 4 parameters.
     */
    public BookingServiceImpl(BookingDao bookingDao, UsrDao usrDao, HotelDao hotelDao, RoomDao roomDao, UsrCountryDao usrCountryDao) {
        this.bookingDao = bookingDao;
        this.usrDao = usrDao;
        this.hotelDao = hotelDao;
        this.roomDao = roomDao;
        this.usrCountryDao = usrCountryDao;
    }

    /**
     * Method saved new Booking entity
     *
     * @param bookingReqDto dto from form
     * @param orderDate     date ordered
     */
    @Override
    public void book(BookingReqDto bookingReqDto, LocalDateTime orderDate) {
        LocalDate checkin = bookingReqDto.getCheckin();
        LocalDate checkout = bookingReqDto.getCheckout();
        if (checkin.compareTo(checkout) > 0) {
            throw new InvalidTimePeriod();
        }
        Booking booking = mapToBooking(bookingReqDto, orderDate);
        bookingDao.save(booking);
        usrCountryDao.usrVisitedCountry(booking.getUsr().getId(), booking.getHotel().getCity().getCountry().getId());
    }

    /**
     * Method return Booking entity
     *
     * @param bookingReqDto dto from form
     * @param orderDate     date ordered
     * @return new Booking entity
     */
    private Booking mapToBooking(BookingReqDto bookingReqDto, LocalDateTime orderDate) {
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