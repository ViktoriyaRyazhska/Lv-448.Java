package academy.softserve.museum;

import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Audience;

public class Main {
    public static void main(String[] args) {
        DaoFactory.audienceDao().findByName("TestT");
    }
}
