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
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return getId() == author.getId() &&
                getAuthorFirstName().equals(author.getAuthorFirstName()) &&
                getAuthorLastName().equals(author.getAuthorLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthorFirstName(), getAuthorLastName());
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
