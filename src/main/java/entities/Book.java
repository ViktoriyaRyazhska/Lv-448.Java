package entities;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Long id;
    private int amountOfInstances;
    private String title;
    private LocalDate releaseDate;
    private Author author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return amountOfInstances == book.amountOfInstances &&
                id.equals(book.id) &&
                title.equals(book.title) &&
                releaseDate.equals(book.releaseDate) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountOfInstances, title, releaseDate, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", amountOfInstances=" + amountOfInstances +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", author=" + author +
                '}';
    }
}