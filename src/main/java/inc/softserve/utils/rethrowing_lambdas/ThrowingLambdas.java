package inc.softserve.utils.rethrowing_lambdas;

import java.util.function.Consumer;
import java.util.function.Function;

//@Slf4j
public class ThrowingLambdas {

    public static <T> Consumer<T> consumer(ThrowingConsumer<T, Exception> throwingConsumer) {
        return x -> {
            try {
                throwingConsumer.accept(x);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T1, T2> Function<T1, T2> function(ThrowingFunction<T1, T2, Exception> throwingFunction){
        return x -> {
            try {
                return throwingFunction.apply(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
