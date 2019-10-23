package Dto;

import entities.BookInstance;
import entities.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class OrderDto {
    private Long id;
    private LocalDate dateOrder;
    private LocalDate dateReturn;
    private User user;
    private BookInstance bookInstance;

    public OrderDto(Long id, LocalDate dateOrder, LocalDate dateReturn, User user, BookInstance bookInstance) {
        this.id = id;
        this.dateOrder = dateOrder;
        this.dateReturn = dateReturn;
        this.user = user;
        this.bookInstance = bookInstance;
    }
}
