package inc.softserve.utils.rethrowing_lambdas;

import java.util.function.Consumer;

public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;
}
