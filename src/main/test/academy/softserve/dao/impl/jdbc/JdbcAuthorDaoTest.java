package academy.softserve.dao.impl.jdbc;

import org.junit.jupiter.api.BeforeAll;

public class JdbcAuthorDaoTest extends DaoTest {

    @BeforeAll
    void init() {
        dropTables();
        createTables();
        fillTables();
    }

    void findExhibitsByAuthor() {

    }

}
