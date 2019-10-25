package dto;

import entities.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookInstanceDto {
    private Long id;
    private Boolean isAvailable;
    private Book book;
    private Long countWasTaken;
}
