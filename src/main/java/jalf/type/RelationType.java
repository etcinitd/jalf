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

public class RelationType extends HeadingBasedType implements Type<Relation> {

    private TupleType tupleType;

    public RelationType(Heading heading) {
        super(heading);
        tupleType = TupleType.heading(heading);
    }

    public static RelationType varargs(Object... args) {
        return new RelationType(Heading.varargs(args));
    }

    public static RelationType heading(Heading heading) {
        return new RelationType(heading);
    }

    public static RelationType leastCommonSupertype(RelationType r1, RelationType r2) {
        validateNotNull("Argument r1 must not be null", r1);
        validateNotNull("Argument r2 must not be null", r2);

        Heading h = Heading.leastCommonSuperHeading(r1.getHeading(), r2.getHeading());
        return RelationType.heading(h);
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
