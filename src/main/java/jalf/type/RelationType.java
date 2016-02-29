package jalf.type;

import static jalf.util.ValidationUtils.validateNotNull;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.Type;
import jalf.TypeException;
import jalf.aggregator.Aggregator;

/**
 * Relation type, captures the possible types of relations.
 *
 * A relation type defines the 'structure' of a relation, in terms of a heading.
 */
public class RelationType extends HeadingBasedType implements Type<Relation> {

    private TupleType tupleType;

    private RelationType(Heading heading) {
        super(heading);
        tupleType = TupleType.dress(heading);
    }

    /**
     * Factors a relation type from a given heading.
     *
     * @param heading a heading instance.
     * @return the factored relation type.
     */
    public static RelationType dress(Heading heading) {
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
    public static RelationType dress(Object... nameTypePairs) {
        return new RelationType(Heading.dress(nameTypePairs));
    }

    /**
     * Computes the least common super type (`lcst`) of `r1` and `r2`.
     *
     * Note: as JAlf does not support type inheritance for now, this method will
     * throw a TypeException unless `r1` and `r2` capture the exact same type.
     *
     * @pre `r1` and `r2` must have compatible headings, that is, they must
     * have the same set of attribute names and compatible types for each of
     * them according to `Type.leastCommonSupertype`.
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
        return RelationType.dress(lcsh);
    }

    /**
     * Infers a relation type from a stream of tuples.
     *
     * @pre all tuples must be heading-compatible, i.e. they must have same
     * set of attribute names and compatible types for each of them according
     * to `Type.leastCommonSuperType`.
     * @pre the stream of tuples may not be empty.
     * @param of a stream of tuples.
     * @return the inferred relation type.
     * @throws TypeException if any precondition is violated.
     */
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
        return RelationType.dress(r.getHeading());
    }

    /**
     * Returns the corresponding tuple type.
     *
     * @return the type of the relation tuples.
     */
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

    /**
     * Projects this type on a subset of its attributes.
     *
     * @pre attributes should be a subset of the type's attribute names.
     * @param on a set of attribute names.
     * @return the projected type.
     */
    public RelationType project(AttrList on) {
        checkValidAttrList(on);
        return new RelationType(heading.project(on));
    }

    /**
     * Projects this type on a subset of its attributes.
     *
     * @pre attributes should be a subset of the type's attribute names.
     * @param on a set of attribute names.
     * @return the by type and field aggregator type.
     *
     */

    public RelationType summarize(AttrList by,Aggregator<?> agg, AttrName as) {
        AttrList l = by;
        if( agg.getAggregatedField() !=null){
            l = by.union(AttrList.attrs(agg.getAggregatedField()));
        }
        // check if the by+aggregated attr is valid
        checkValidAttrList(l);

        // check if by+aggregated attr don't contain as
        if (l.contains(as))
            throw new TypeException("By can't contain as: by " + by + " as " + as);

        // check if the aggregator can aggregate on the aggregated attr
        if (agg.notAllowedAggrAttr(this))
            throw new TypeException("Aggregator can't aggregate on the on attr " + agg.getAggregatedField());

        return new RelationType(heading.summarize(by, as, agg.getResultingType(this)));
    }

    /**
     * Returns the type obtained by renaming some of its attributes.
     *
     * @param renaming a renaming function.
     * @return the relation type where attribute have been renamed according to
     * `renaming`.
     */
    public RelationType rename(Renaming renaming) {
        return new RelationType(heading.rename(renaming));
    }

    /**
     * Returns the relation subtype constrained by the given tuple predicate.
     *
     * @pre the predicate must only refer to attributes of the relation type.
     * @param predicate a tuple predicate.
     * @return a subtype of this relation, by constraint `predicate`.
     */
    public RelationType restrict(Predicate predicate) {
        AttrList on = predicate.getReferencedAttributes();
        checkValidAttrList(on);
        return this;
    }

    /**
     * Computes the type resulting from a join with another relation type.
     *
     * @param type another relation type
     * @return the type resulting from a relational join.
     */
    public RelationType join(RelationType other) {
        return new RelationType(this.heading.join(other.heading));
    }

    public RelationType union(RelationType other) {
        return leastCommonSupertype(this,other);
    }

    public RelationType intersect(RelationType other) {
        return leastCommonSupertype(this,other);
    }

    public RelationType minus(RelationType other) {
        return leastCommonSupertype(this,other);
    }

    public AttrList toAttrList() {
        return heading.toAttrList();
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

    ///

    /**
     * Checks that `on` is a subset of this relation type attributes or throws
     * a TypeException with a friendly message.
     */
    private void checkValidAttrList(AttrList on) {
        AttrList mine = toAttrList();
        AttrList extra = on.difference(mine);
        if (!extra.isEmpty()) {
            String which = extra.stream()
                    .map(a -> a.getName())
                    .collect(Collectors.joining(", "));
            throw new TypeException("No such attributes: " + which);
        }
    }

}
