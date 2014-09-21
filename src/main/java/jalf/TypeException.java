package jalf;

/**
 * Thrown when a type-checking procedure fails.
 */
public class TypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TypeException() {
        super();
    }

    public TypeException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }

}
