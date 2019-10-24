package inc.softserve.exceptions;

public class ContextParameterNotFound extends RuntimeException {

    public ContextParameterNotFound() {
        super("Context parameter has not been found");
    }
}
