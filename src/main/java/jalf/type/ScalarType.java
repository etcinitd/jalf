package jalf.type;

import jalf.Type;

public class ScalarType<T> implements Type<T> {

    private Class<T> representation;

    public ScalarType(Class<T> representation) {
        this.representation = representation;
    }

    @Override
    public Class<T> getRepresentationClass() {
        return representation;
    }

}
