package dto;

import entities.BookInstance;
import entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class OrderDto {
    private Long id;
    private LocalDate dateOrder;
    private LocalDate dateReturn;
    private User user;
    private BookInstance bookInstance;

}

