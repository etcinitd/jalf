package jalf;

import jalf.type.ScalarType;

public interface Type<T> {

    default <U> Type<U> scalar(Class<U> representation) {
        return new ScalarType<U>(representation);
    }

    public Class<T> getRepresentationClass();

}
