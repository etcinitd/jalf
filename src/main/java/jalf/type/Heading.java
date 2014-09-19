package jalf.type;

import jalf.AttrName;
import jalf.Type;

import java.util.Map;

public class Heading {

    private Map<AttrName, Type<?>> attributes;

    public Heading(Map<AttrName, Type<?>> attributes) {
        this.attributes = attributes;
    }

    public Type<?> getTypeOf(AttrName attrName) {
        return attributes.get(attrName);
    }

}
