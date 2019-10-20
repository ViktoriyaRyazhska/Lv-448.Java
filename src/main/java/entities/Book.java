package entities;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

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
    private Set<BookInstance> bookInstanceSet;
    private Author author;
    private Set<Author> subAuthor;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return amountOfInstances == book.amountOfInstances &&
                id.equals(book.id) &&
                title.equals(book.title) &&
                releaseDate.equals(book.releaseDate) &&
                Objects.equals(bookInstanceSet, book.bookInstanceSet) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountOfInstances, title, releaseDate, bookInstanceSet, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", amountOfInstances=" + amountOfInstances +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", bookInstanceSet=" + bookInstanceSet +
                ", author=" + author +
                '}';
    }
}