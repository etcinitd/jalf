package jalf.type;

import static jalf.util.ValidationUtils.validateNotNull;
import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.Type;
import jalf.TypeException;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Relation type, captures the possible types of relations.
 *
 * A relation type defines the 'structure' of a relation, in terms of a heading.
 */
public class RelationType extends HeadingBasedType implements Type<Relation> {

    private TupleType tupleType;

    private RelationType(Heading heading) {
        super(heading);
        tupleType = TupleType.heading(heading);
    }

    /**
     * Factors a relation type from a given heading.
     *
     * @param heading a heading instance.
     * @return the factored relation type.
     */
    public static RelationType heading(Heading heading) {
        return new RelationType(heading);
    }

    /**
     * Factors a relation type from a list of attribute (name, type) pairs.
     *
     * @pre pairs must have an even size. Odd elements must be attribute names,
     * even elements must be types.
     * @param nameTypePairs the list of pairs to convert to a relation type.
     * @return factored relation type.
     */
    public static RelationType varargs(Object... nameTypePairs) {
        return new RelationType(Heading.varargs(nameTypePairs));
    }

    /**
     * Computes the least common super type (`lcst`) of `r1` and `r2`.
     *
     * The two relation types must have compatible headings, that is, they must
     * have the same set of attribute names and compatible types (in terms of
     * Type.leastCommonSuperType) for each of them.
     *
     * Note: as JAlf does not support type inheritance for now, this method will
     * throw a TypeException unless `r1` and `r2` capture the exact same type.
     *
     * @param r1 a relation type.
     * @param r2 another relation type.
     * @return the least common super type of `r1` and `r2`.
     */
    public static RelationType leastCommonSupertype(RelationType r1, RelationType r2) {
        validateNotNull("Argument r1 must not be null", r1);
        validateNotNull("Argument r2 must not be null", r2);

        Heading h1 = r1.getHeading();
        Heading h2 = r2.getHeading();
        Heading lcsh = Heading.leastCommonSuperHeading(h1, h2);
        return RelationType.heading(lcsh);
    }

    public static RelationType infer(Stream<Tuple> of) {
        TupleType identity = null;
        Function<Tuple,TupleType> mapper = (t) -> t.getType();
        BinaryOperator<TupleType> op = (t1, t2) -> {
            if (t1 == null) return t2;
            if (t2 == null) return t1;
            return TupleType.leastCommonSupertype(t1, t2);
        };
        TupleType r = of.collect(Collectors.reducing(identity, mapper, op));
        if (r == null) {
            throw new TypeException("Unable to infer relation type: stream of tuples may not be empty");
        }
        return RelationType.heading(r.getHeading());
    }

    public TupleType toTupleType() {
        return tupleType;
    }

    @Override
    public Class<Relation> getRepresentationClass() {
        return Relation.class;
    }

    @Override
    public boolean contains(Object value) {
        return (value instanceof Relation) &&
                ((Relation)value).getType().isSubTypeOf(this);
    }

    public RelationType project(AttrList attributes) {
        return new RelationType(heading.project(attributes));
    }

    public RelationType rename(Renaming renaming) {
        return new RelationType(heading.rename(renaming));
    }

    public RelationType restrict(Predicate predicate) {
        return this;
    }

    @Override
    public int hashCode() {
        return 31*RelationType.class.hashCode() + heading.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof RelationType))
            return false;
        RelationType other = (RelationType) obj;
        return heading.equals(other.heading);
    }

}
