package entities;

import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksSubAuthors {

    private Long id;
    private Book books;
    private Author subAuthors;

}
