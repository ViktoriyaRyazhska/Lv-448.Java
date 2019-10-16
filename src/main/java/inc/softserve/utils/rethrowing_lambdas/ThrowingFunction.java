package inc.softserve.utils.rethrowing_lambdas;

public interface ThrowingFunction<T1, T2, E extends Exception> {

    T2 apply(T1 t1) throws E;
}
