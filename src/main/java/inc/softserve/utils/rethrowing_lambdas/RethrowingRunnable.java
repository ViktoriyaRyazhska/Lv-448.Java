package inc.softserve.utils.rethrowing_lambdas;

/**
 * Runner wrapper. Rethrows checked exceptions into runtime ones.
 * @param <E> - checked exception.
 */
@FunctionalInterface
public interface RethrowingRunnable<E extends Exception> {

    void run() throws E;
}
