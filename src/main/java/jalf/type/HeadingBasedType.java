package jalf.type;

import jalf.AttrName;
import jalf.Type;
import jalf.TypedAttrName;

public class HeadingBasedType {

    protected Heading heading;

    public HeadingBasedType(Heading heading) {
        this.heading = heading;
    }

    public Heading getHeading() {
        return heading;
    }

    public Type<?> getTypeOf(AttrName attrName) {
        return heading.getTypeOf(attrName);
    }

    public <T> Type<T> getTypeOf(TypedAttrName<T> attrName) {
        return heading.getTypeOf(attrName);
    }

}