package jalf.type;

import jalf.Tuple;
import jalf.Type;

public class TupleType extends HeadingBasedType implements Type<Tuple> {

    public TupleType(Heading heading) {
        super(heading);
    }

    @Override
    public Class<Tuple> getRepresentationClass() {
        return Tuple.class;
    }

}
