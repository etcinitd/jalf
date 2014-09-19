package jalf;

import static jalf.util.ValidationUtils.validateNotNull;
import jalf.type.ScalarType;

public interface Type<T> {

    /**
     * Builds a scalar type.
     *
     * @param representation the Java class used to represent values.
     * @return a Type instance.
     */
    static <U> Type<U> scalar(Class<U> representation) {
        validateNotNull("Argument representation must not be null", representation);

        // TODO use a concurrent WeakHashMap to avoid creating the same type a million times
        return new ScalarType<U>(representation);
    }

    /**
     * Dress `obj` as a type, applying the necessary coercions.
     *
     * @pre `obj` must already be a type or must match one of the factory methods.
     * @param obj an object to dress as a Type.
     * @return a Type instance.
     */
    static Type<?> dress(Object obj) {
        if (obj instanceof Type)
            return (Type<?>) obj;
        if (obj instanceof Class)
            return scalar((Class<?>)obj);
        throw new IllegalArgumentException("Unable to dress `" + obj + "` to Type");
    }

    /**
     * Infer the type of `value`.
     *
     * @param value any java object.
     * @return a JAlf type to use for `value`
     */
    static Type<?> infer(Object value) {
        validateNotNull("Argument value must not be null", value);

        if (value instanceof Tuple)
            return ((Tuple) value).getType();
        if (value instanceof Relation)
            return ((Relation) value).getType();
        return scalar(value.getClass());
    }

    /**
     * Computes the least common super type between t1 and t2.
     *
     * This method requires t1 and t2 to be equal, meaning that no type
     * inheritance is implemented for now. Future versions of JAlf will
     * perhaps introduce a better support for type inheritance.
     *
     * @param t1 any JAlf type.
     * @param t2 any JAlf type.
     * @return the first common type in the hierarchy of t1 and t2.
     * @throws TypeException of no such type can be found.
     */
    public static Type<?> leastCommonSupertype(Type<?> t1, Type<?> t2) throws TypeException {
        validateNotNull("Argument t1 must not be null", t1);
        validateNotNull("Argument t2 must not be null", t2);

        if (t1.equals(t2)) {
            return t1;
        } else {
            throw new TypeException("No common super type for `" + t1 + "` and `" + t2 + "`");
        }
    }

    /**
     * Computes the least common super class between `c1` and `c2`.
     *
     * @param c1 any java class.
     * @param c2 any java class.
     * @return the first common class in the hierarchy of c1 and c2.
     */
    public static Class<?> leastCommonSuperclass(Class<?> c1, Class<?> c2) {
        validateNotNull("Argument c1 must not be null", c1);
        validateNotNull("Argument c2 must not be null", c2);

        if (c1.isAssignableFrom(c2)) return c1;
        if (c2.isAssignableFrom(c1)) return c2;
        return leastCommonSuperclass(c1.getSuperclass(), c2.getSuperclass());
    }

    /**
     * Returns the java class used for representing values of this type.
     *
     * @return the representation class.
     */
    public Class<T> getRepresentationClass();

}
