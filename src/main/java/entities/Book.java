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
    private String category;
    private Set<BookInstance> bookInstanceSet;
    private Author author;
    private Set<Author> subAuthors;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return amountOfInstances == book.amountOfInstances &&
                id.equals(book.id) &&
                title.equals(book.title) &&
                releaseDate.equals(book.releaseDate) &&
                category.equals(book.category) &&
                bookInstanceSet.equals(book.bookInstanceSet) &&
                author.equals(book.author) &&
                subAuthors.equals(book.subAuthors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountOfInstances, title, releaseDate, category, bookInstanceSet, author, subAuthors);
    }
}