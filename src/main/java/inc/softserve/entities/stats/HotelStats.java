package inc.softserve.entities.stats;

import inc.softserve.entities.stats.time_utils.Day;
import lombok.Builder;
import lombok.Data;

@Data
public class HotelStats {

    private final Long hotelId;
    private final String hotelName;
    private final int clients;
    private final Day averageBookingTime;

    @Builder
    public HotelStats(Long hotelId, String hotelName, int clients, Day averageBookingTime) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.clients = clients;
        this.averageBookingTime = averageBookingTime;
    }
}