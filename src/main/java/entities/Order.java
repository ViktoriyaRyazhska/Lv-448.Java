package entities;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private long id;
    private LocalDate dateOrder;
    private LocalDate dateReturn;
    private User user;
    private BookInstance bookInstance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                dateOrder.equals(order.dateOrder) &&
                dateReturn.equals(order.dateReturn) &&
                user.equals(order.user) &&
                bookInstance.equals(order.bookInstance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOrder, dateReturn, user, bookInstance);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateOrder=" + dateOrder +
                ", dateReturn=" + dateReturn +
                ", user=" + user +
                ", bookInstance=" + bookInstance +
                '}';
    }
}
