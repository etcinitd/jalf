package jalf.type;

import static jalf.util.ValidationUtils.validateNotNull;
import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;

import java.util.Map;

public class TupleType extends HeadingBasedType implements Type<Tuple> {

    public TupleType(Heading heading) {
        super(heading);
    }

    public static TupleType heading(Heading h) {
        return new TupleType(h);
    }

    public static TupleType varargs(Object... nameTypePairs) {
        return heading(Heading.varargs(nameTypePairs));
    }

    public static TupleType infer(Map<AttrName, Object> keyValuePairs) {
        return new TupleType(Heading.infer(keyValuePairs));
    }

    public static TupleType leastCommonSupertype(TupleType t1, TupleType t2) {
        validateNotNull("Argument t1 must not be null", t1);
        validateNotNull("Argument t2 must not be null", t2);

        Heading h = Heading.leastCommonSuperHeading(t1.getHeading(), t2.getHeading());
        return TupleType.heading(h);
    }

    @Override
    public Class<Tuple> getRepresentationClass() {
        return Tuple.class;
    }

    @Override
    public int hashCode() {
        return 31*TupleType.class.hashCode() + heading.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof TupleType))
            return false;
        TupleType other = (TupleType) obj;
        return heading.equals(other.heading);
    }

}
