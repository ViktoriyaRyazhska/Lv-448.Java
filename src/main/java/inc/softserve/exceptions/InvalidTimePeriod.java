package inc.softserve.exceptions;

public class InvalidTimePeriod extends RuntimeException {

    public InvalidTimePeriod() {
        super("Checkin cannot be before than checkout");
    }
}
