package dto;

import entities.Author;
import entities.BookInstance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private Long amountOfTimesBookWasTaken;
    private String averageTimeReading;
    private List<BookInstanceDto> bookInstance;

}
