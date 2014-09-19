package jalf.type;

import jalf.AttrName;
import jalf.Type;

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

}