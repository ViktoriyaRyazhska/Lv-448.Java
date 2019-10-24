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

    private Long id;
    private LocalDate dateOrder;
    private LocalDate dateReturn;
    private User user;
    private BookInstance bookInstance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) &&
                Objects.equals(dateOrder, order.dateOrder) &&
                Objects.equals(dateReturn, order.dateReturn) &&
                Objects.equals(user, order.user) &&
                Objects.equals(bookInstance, order.bookInstance);
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
