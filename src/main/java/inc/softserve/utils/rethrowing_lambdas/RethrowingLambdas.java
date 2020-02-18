package inc.softserve.utils.rethrowing_lambdas;

import java.util.function.Consumer;
import java.util.function.Function;

//@Slf4j
public class RethrowingLambdas {

    private RethrowingLambdas() {
    }

    public static <T> Consumer<T> consumer(final RethrowingConsumer<T, Exception> rethrowingConsumer) {
        return x -> {
            try {
                rethrowingConsumer.accept(x);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T1, T2> Function<T1, T2> function(final RethrowingFunction<T1, T2, Exception> rethrowingFunction) {
        return x -> {
            try {
                return rethrowingFunction.apply(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void runnable(final RethrowingRunnable<Exception> rethrowingRunnable) {
        try {
            rethrowingRunnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
