package entities;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    private long id;
    private String authorFirstName;
    private String authorLastName;
    private Set<Book> books;


   }
