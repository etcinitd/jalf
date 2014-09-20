package jalf.type;

import jalf.Type;

/**
 * A scalar type, i.e. everything but a tuple or relation type.
 *
 * ScalarType wrap a java class as a JAlf type. The class must implement a
 * proper set of values (sometimes called `value objects`), in particular
 * it must implement equals and hashCode with a value semantics.
 *
 * @param <T> the class used for representing values of this type.
 */
public class ScalarType<T> implements Type<T> {

    private Class<T> representation;

    public ScalarType(Class<T> representation) {
        this.representation = representation;
    }

    @Override
    public Class<T> getRepresentationClass() {
        return representation;
    }

    @Override
    public boolean contains(Object value) {
        return representation.isInstance(value);
    }

    @Override
    public int hashCode() {
        return 31*ScalarType.class.hashCode() + representation.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof ScalarType))
            return false;
        ScalarType<?> other = (ScalarType<?>) obj;
        return representation.equals(other.representation);
    }

}
