package inc.softserve.utils.rethrowing_lambdas;

/**
 * Function wrapper. Rethrows checked exceptions into runtime ones.
 * @param <T1> - function's parameter type
 * @param <T2> - function's return type
 * @param <E> - checked exception
 */
@FunctionalInterface
public interface RethrowingFunction<T1, T2, E extends Exception> {

    T2 apply(T1 t1) throws E;
}
