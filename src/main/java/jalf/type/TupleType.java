package jalf.type;

import static jalf.util.ValidationUtils.validateNotNull;
import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;

import java.util.Map;

public class TupleType extends HeadingBasedType implements Type<Tuple> {

    private TupleType(Heading heading) {
        super(heading);
    }

    /**
     * Factors a tuple type from a given heading.
     *
     * @param heading a heading instance.
     * @return the factored relation type.
     */
    public static TupleType heading(Heading h) {
        return new TupleType(h);
    }

    /**
     * Factors a tuple type from a list of attribute (name, type) pairs.
     *
     * @pre pairs must have an even size. Odd elements must be attribute names,
     * even elements must be types.
     * @param nameTypePairs the list of pairs to convert to a tuple type.
     * @return factored tuple type.
     */
    public static TupleType varargs(Object... nameTypePairs) {
        return heading(Heading.varargs(nameTypePairs));
    }

    /**
     * Infers a tuple type from a map of attribute (name, value).
     *
     * @param keyValuePairs a map of name and values.
     * @return the inferred tuple type.
     */
    public static TupleType infer(Map<AttrName, Object> keyValuePairs) {
        return new TupleType(Heading.infer(keyValuePairs));
    }

    /**
     * Computes the least common super type of `t1` and `t2`.
     *
     * @pre `t1` and `t2` must be heading-compatible, i.e. they must have same
     * set of attribute names and compatible types for each of them, according
     * to `Type.leastCommonSupertype`.
     * @param t1 a tuple type.
     * @param t2 another type type.
     * @return their least common super type.
     */
    public static TupleType leastCommonSupertype(TupleType t1, TupleType t2) {
        validateNotNull("Argument t1 must not be null", t1);
        validateNotNull("Argument t2 must not be null", t2);

        Heading h1 = t1.getHeading();
        Heading h2 = t2.getHeading();
        Heading h = Heading.leastCommonSuperHeading(h1, h2);
        return TupleType.heading(h);
    }

    @Override
    public Class<Tuple> getRepresentationClass() {
        return Tuple.class;
    }

    @Override
    public boolean contains(Object value) {
        return (value instanceof Tuple) &&
                ((Tuple)value).getType().isSubTypeOf(this);
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
