package inc.softserve.dao.db_test_utils;

import inc.softserve.utils.rethrowing_lambdas.ThrowingLambdas;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FieldChecked {

    public static <T> void assertFieldValues(Stream<T> entityStream, Predicate<Field> skipFields, Consumer<Object> assertion){
        entityStream
                .flatMap(entity -> Stream.of(entity
                        .getClass()
                        .getDeclaredFields())
                        .filter(skipFields)
                        .map(ThrowingLambdas.function(field -> {
                            field.setAccessible(true);
                            return field.get(entity);
                        }))
                ).forEach(assertion);
    }
}
