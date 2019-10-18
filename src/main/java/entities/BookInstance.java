package entities;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookInstance {
    private Long id;
    private Boolean isAvailable;
    private Long bookId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInstance that = (BookInstance) o;
        return id.equals(that.id) &&
                Objects.equals(isAvailable, that.isAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isAvailable);
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "id=" + id +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
