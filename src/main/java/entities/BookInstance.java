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
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInstance that = (BookInstance) o;
        return id.equals(that.id) &&
                isAvailable.equals(that.isAvailable) &&
                book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isAvailable, book);
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "id=" + id +
                ", isAvailable=" + isAvailable +
                ", book=" + book +
                '}';
    }
}
