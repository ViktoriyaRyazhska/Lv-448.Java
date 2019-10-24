package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorDto {
    private Long id;
    private String name;
    private String surname;
    private Integer averageAgeUser;

    public AuthorDto(Long id, String name, String surname, Integer averageAgeUser) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.averageAgeUser = averageAgeUser;
    }
}
