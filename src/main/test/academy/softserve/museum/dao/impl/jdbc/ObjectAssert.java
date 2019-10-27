package academy.softserve.museum.dao.impl.jdbc;

@FunctionalInterface
public interface ObjectAssert<T> {

    void assertObjectsEquals(T a, T b);

}
