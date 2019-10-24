package Dto;

import entities.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class BookDto {
    private Long id;
    private Integer amountOfInstances;
    private String title;
    private LocalDate releaseDate;
    private Author author;
    private String isAvailable;

    public BookDto(Long id, Integer amountOfInstances, String title, LocalDate releaseDate, Author author, String isAvailable) {
        this.id = id;
        this.amountOfInstances = amountOfInstances;
        this.title = title;
        this.releaseDate = releaseDate;
        this.author = author;
        this.isAvailable = isAvailable;
    }

}
