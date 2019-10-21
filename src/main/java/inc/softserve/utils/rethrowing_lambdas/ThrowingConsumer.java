package inc.softserve.utils.rethrowing_lambdas;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;
}
