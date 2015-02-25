package jalf;

import static jalf.util.ValidationUtils.validateNotNull;
import jalf.type.Heading;
import jalf.type.RelationType;
import jalf.type.ScalarType;
import jalf.type.TupleType;

/**
 * JAl's type abstraction, i.e. a set of values.
 *
 * This is the main interface of JAlf's type system. Conceptually, a JAlf type
 * is a set of immutable values. JAlf currently distinguishes between scalar,
 * tuple and relation types. The last two are implemented by JAlf own classes,
 * see TupleType and RelationType. In contrast, scalar types can be obtained by
 * simple decoration of arbitrary Java classes. The latter must however properly
 * implement so-called value objects. In particular, the objects must be
 * immutable (or not mutated in practice) and their class must provide
 * consistent implementations of `hashCode` and `equals`. Provided those
 * requirements are met, any class/object can be used for capturing values in
 * JAlf tuples and relations.
 *
 * This interface is not intended to be instantiated directly. Instances may be
 * obtained through the factory methods.
 *
 * @param <T> the java class used for representing the values of this type.
 */
public interface Type<T> {

    /**
     * Builds a scalar type.
     *
     * @param representation the Java class used to represent values.
     * @return a Type instance.
     */
    static <U> Type<U> scalarType(Class<U> representation) {
        validateNotNull("Argument representation must not be null", representation);

        // TODO use a concurrent WeakHashMap to avoid creating the same type a million times
        return new ScalarType<U>(representation);
    }

    /**
     * Factors a tuple type for a given heading
     *
     * @param heading a heading.
     * @return factored tuple type instance.
     */
    static TupleType tupleType(Heading heading) {
        return TupleType.dress(heading);
    }

    /**
     * Factors a tuple type from a list of attribute (name, type) pairs.
     *
     * @pre pairs must have an even size. Odd elements must be attribute names,
     * even elements must be types.
     * @param nameTypePairs the list of pairs to convert to a tuple type.
     * @return factored tuple type.
     */
    static TupleType tupleType(Object... nameTypePairs) {
        return TupleType.dress(nameTypePairs);
    }

    /**
     * Factors a relation type from a given heading.
     *
     * @param heading a heading instance.
     * @return the factored relation type.
     */
    static RelationType relationType(Heading heading) {
        return RelationType.dress(heading);
    }

    /**
     * Factors a relation type from a list of attribute (name, type) pairs.
     *
     * @pre pairs must have an even size. Odd elements must be attribute names,
     * even elements must be types.
     * @param nameTypePairs the list of pairs to convert to a relation type.
     * @return factored relation type.
     */
    static RelationType relationType(Object... nameTypePairs) {
        return RelationType.dress(nameTypePairs);
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
            return scalarType((Class<?>)obj);
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
        return scalarType(value.getClass());
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
    static Type<?> leastCommonSupertype(Type<?> t1, Type<?> t2) throws TypeException {
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
    static Class<?> leastCommonSuperclass(Class<?> c1, Class<?> c2) {
        validateNotNull("Argument c1 must not be null", c1);
        validateNotNull("Argument c2 must not be null", c2);

        if (c1.isAssignableFrom(c2)) return c1;
        if (c2.isAssignableFrom(c1)) return c2;
        return leastCommonSuperclass(c1.getSuperclass(), c2.getSuperclass());
    }

    /**
     * Checks if this type is a sub type of `type`.
     *
     * Note: JAlf does not support type inheritance so far, so this method
     * returns true only if `type` is equal to `this`.
     *
     * @param type another type.
     * @return true if this is a sub type of `type`, false otherwise.
     */
    default boolean isSubTypeOf(Type<?> type) {
        // TODO: properly implement type inheritance (Type#isSubTypeOf)
        return equals(type);
    }

    /**
     * Returns the java class used for representing values of this type.
     *
     * @return the representation class.
     */
    Class<T> getRepresentationClass();

    /**
     * Checks if this type contains `value`, that is, if `value` belongs to the
     * set of values captured by this type.
     *
     * Note: this method is not parameterized by T by intent, as it is used in
     * many places where types are used in an "unsafe" way. That said, a
     * necessary condition for this method to return true is for `value` to be
     * an instance of T. Observe that such a condition is not sufficient, e.g.
     * relation and tuple types impose further conditions.
     *
     * @param value an arbitrary value (null supported)
     * @return true if `value` belongs to this, false otherwise.
     */
    boolean contains(Object value);

}
