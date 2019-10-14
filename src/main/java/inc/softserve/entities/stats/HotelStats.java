package inc.softserve.entities.stats;

import inc.softserve.entities.stats.time_utils.Day;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HotelStats {

    private final String hotelName;
    private final int clients;
    private final Day averageBookingTime;

    @Builder
    public HotelStats(String hotelName, int clients, Day averageBookingTime) {
        this.hotelName = hotelName;
        this.clients = clients;
        this.averageBookingTime = averageBookingTime;
    }
}
