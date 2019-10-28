package inc.softserve.utils.rethrowing_lambdas;

@FunctionalInterface
public interface RethrowingRunnable<E extends Exception> {

    void run() throws E;
}
