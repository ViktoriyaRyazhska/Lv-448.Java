package inc.softserve.utils.rethrowing_lambdas;

/**
 * Consumer wrapper. Rethrows checked exceptions into runtime ones.
 * @param <T> - consumer's parameter type
 * @param <E> - checked exception
 */
@FunctionalInterface
public interface RethrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;
}
