package inc.softserve.security;

import java.util.function.Supplier;

/**
 * This function should be used for generating salt
 */
@FunctionalInterface
public interface SaltGen extends Supplier<String> {
}
