package entities;

import lombok.*;

import java.time.LocalDate;
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




}