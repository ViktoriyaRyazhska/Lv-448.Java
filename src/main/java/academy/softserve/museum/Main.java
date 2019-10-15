package academy.softserve.museum;

import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;

public class Main {
    public static void main(String[] args) {
//        Author author = new Author();
//        Exhibit exhibit = new Exhibit();
//        exhibit.setId(3);
//        author.setId(1);

        DaoFactory.authorDao().update(new Author(17, "test", "test"));
    }
}
