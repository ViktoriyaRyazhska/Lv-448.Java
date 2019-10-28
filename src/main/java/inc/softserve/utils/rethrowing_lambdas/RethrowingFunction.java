package inc.softserve.utils.rethrowing_lambdas;

@FunctionalInterface
public interface RethrowingFunction<T1, T2, E extends Exception> {

    T2 apply(T1 t1) throws E;
}
