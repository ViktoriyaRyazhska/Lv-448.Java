package inc.softserve.security;

import java.util.function.Supplier;

@FunctionalInterface
public interface SaltGen extends Supplier<String> {
}
