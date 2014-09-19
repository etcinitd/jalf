package jalf.type;

import jalf.AttrName;
import jalf.Type;
import jalf.TypedAttrName;

import java.util.Map;

public class Heading {

    private Map<AttrName, Type<?>> attributes;

    public Heading(Map<AttrName, Type<?>> attributes) {
        this.attributes = attributes;
    }

    public Type<?> getTypeOf(AttrName attrName) {
        return attributes.get(attrName);
    }

    @SuppressWarnings("unchecked")
    public <T> Type<T> getTypeOf(TypedAttrName<T> attrName) {
        return (Type<T>) attributes.get(attrName);
    }

}
