package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private Long id;
    private int amountOfInstances;
    private String title;
    private LocalDate releaseDate;
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmountOfInstances() {
        return amountOfInstances;
    }

    public void setAmountOfInstances(int amountOfInstances) {
        this.amountOfInstances = amountOfInstances;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateOfRelease() {
        return releaseDate;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.releaseDate = dateOfRelease;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getAmountOfInstances() == book.getAmountOfInstances() &&
                Objects.equals(getId(), book.getId()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getDateOfRelease(), book.getDateOfRelease()) &&
                Objects.equals(getCategory(), book.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountOfInstances, title, releaseDate, category);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", amountOfInstances=" + amountOfInstances +
                ", title='" + title + '\'' +
                ", dateOfRelease=" + releaseDate +
                ", category='" + category + '\'' +
                '}';
    }
}
//    create table books
//        (
//                id                  bigint primary key auto_increment,
//                amount_of_instances int          not null,
//                title               varchar(255) not null,
//        date_of_publication date         not null,
//        category            varchar(255) not null,
//        id_author           bigint,
//        foreign key (id_author) references authors (id)
//        );    private Long id;
//private String name;
//private String surname;
//private LocalDate birthday;
//private LocalDate registrationDate;
//private String phoneNumber;
//private String email;