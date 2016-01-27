package jalf.compiler;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.Selection;
import jalf.Tuple;
import jalf.aggregator.Aggregator;
import jalf.relation.algebra.Intersect;
import jalf.relation.algebra.Join;
import jalf.relation.algebra.Minus;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;
import jalf.relation.algebra.Select;
import jalf.relation.algebra.Summarize;
import jalf.relation.algebra.Union;
import jalf.type.TupleType;

/**
 * Parent (abstract) class of all relation implementations.
 *
 * This class provides a base implementation for the Relation contract,
 * especially regarding the Object-Oriented algebra API. The stream of tuples
 * is offered through a compilation process implemented by JAlf default
 * compiler.
 */
public abstract class AbstractRelation implements Relation {

    private TupleType tupleType;

    @Override
    public TupleType getTupleType() {
        if (tupleType == null) {
            tupleType = getType().toTupleType();
        }
        return tupleType;
    }

    ///

    @Override
    public Relation select(Selection selection) {
        return new Select(this, selection);
    }

    @Override
    public Relation project(AttrList attributes) {
        return new Project(this, attributes);
    }

    @Override
    public Relation summarize(AttrList by,Aggregator<?> agg, AttrName as) {
        return new Summarize(this, by,agg, as);
    }

    @Override
    public Relation rename(Renaming renaming) {
        return new Rename(this, renaming);
    }

    @Override
    public Relation restrict(Predicate predicate) {
        return new Restrict(this, predicate);
    }

    @Override
    public Relation join(Relation right) {
        return new Join(this, right);
    }

    @Override
    public Relation union(Relation right) {
        return new Union(this, right);
    }

    @Override
    public Relation intersect(Relation right){
        return new Intersect(this, right);
    }

    @Override
    public Relation minus(Relation right) {
        return new Minus(this, right);
    }

    ///

    @Override
    public Stream<Tuple> stream() {
        return compile().stream();
    }

    @Override
    public long cardinality() {
        return stream().count();
    }

    ///

    private Cog compile() {
        return accept(new Compiler());
    }

    ///

    /**
     * As all Relation(s) denote values (even if some of them are lazy evaluated)
     * we want equals() to apply to those values independently of their actual
     * representation. This commit implements this on the basic infrastructure,
     * making the acceptance test pass for now.
     *
     * The following logic is currently implemented:
     *
     * 1. AbstractRelation checks trivial cases then delegates to the abstract
     *    `equals(Relation)` method.
     * 2. SetMemoryRelation implements two strategies:
     *    2.1 against another set-based (through underlying collection equality)
     *    2.2 against another relation (through memory reload).
     * 3. Operator implements two strategies too:
     *    3.1 delegates to the in-memory `other` relation if it is one
     *    3.2 loads itself into memory to make such a delegate call.
     *
     * TODO: Take relation type into account when computing equality.
     * TODO: The current strategy is not optimal at all, even if it basically
     * works. Possible optimizations include:
     *
     * 1. implement comparison of ordered streams instead of 2.2. and 3.2.
     * 2. avoid the delegation complexity by extracting equals responsibility
     *    to another abstraction (?)
     */
    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null || !(other instanceof Relation))
            return false;
        return equals((Relation) other);
    }

    public abstract boolean equals(Relation other);

    @Override
    public String toString() {
        String s = "relation(\n  ";
        s += stream()
                .map(Object::toString)
                .collect(Collectors.joining(",\n  "));
        s += "\n)";
        return s;
    }


}
