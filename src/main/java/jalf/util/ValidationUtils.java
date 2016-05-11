package jalf.util;

/**
 * @author amirm
 */
public class ValidationUtils {

    public static <T> T validateCast(String msg, Object arg, Class<T> clazz) {
        if (!clazz.isInstance(arg)) {
            throw new IllegalArgumentException(msg);
        }
        return clazz.cast(arg);
    }

    public static void validateNotNull(String msg, Object arg) {
        if (arg == null) {
            throw new NullPointerException(msg);
        }
    }

    public static void validate(String msg, int arg, int expected) {
        if (arg != expected) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void validate(String msg, long arg, long expected) {
        if (arg != expected) {
            throw new IllegalArgumentException(msg);
        }
    }

}
