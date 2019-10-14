package entities;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    private long id;
    private String authorFirstName;
    private String authorLastName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                Objects.equals(authorFirstName, author.authorFirstName) &&
                Objects.equals(authorLastName, author.authorLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorFirstName, authorLastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                '}';
    }
}
