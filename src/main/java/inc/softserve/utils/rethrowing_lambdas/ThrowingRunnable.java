package inc.softserve.utils.rethrowing_lambdas;

@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {

    void run() throws E;
}
