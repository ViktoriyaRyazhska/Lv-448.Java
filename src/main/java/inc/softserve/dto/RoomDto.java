package inc.softserve.dto;

import inc.softserve.dto.on_responce.BookingRespDto;
import inc.softserve.entities.City;
import inc.softserve.entities.Hotel;
import static inc.softserve.entities.Room.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class RoomDto {

    private final Long id;
    private final Integer chamberNumber;
    private final Set<BookingRespDto> bookings;
    private final Luxury luxury;
    private final Bedrooms bedrooms;
    private final Hotel hotel;
    private final City city;

    @Builder
    public RoomDto(Long id, Integer chamberNumber, Set<BookingRespDto> bookings, Luxury luxury, Bedrooms bedrooms, Hotel hotel, City city) {
        this.id = id;
        this.chamberNumber = chamberNumber;
        this.bookings = bookings;
        this.luxury = luxury;
        this.bedrooms = bedrooms;
        this.hotel = hotel;
        this.city = city;
    }
}
