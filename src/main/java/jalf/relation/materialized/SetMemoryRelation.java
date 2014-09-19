package jalf.relation.materialized;

import jalf.Heading;
import jalf.Relation;
import jalf.Tuple;
import jalf.compiler.Cog;
import jalf.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;

import static jalf.util.CollectionUtils.setOf;
import static java.util.Collections.emptyMap;

/**
 * MemoryRelation where an actual set of tuples is used as internal
 * representation.
 */
public class SetMemoryRelation extends MemoryRelation {
    private final Collection<Tuple> tuples;
    private final Heading heading;

    public SetMemoryRelation(Set<Tuple> tuples) {
        this.tuples = tuples;
        if (tuples.size() > 0) {
            this.heading = tuples.iterator().next().heading();
        } else {
            this.heading = Heading.headingOf(emptyMap());
        }
    }

    public SetMemoryRelation(Tuple[] tuples) {
        this(setOf(tuples));
    }

    @Override
    public Heading heading() {
        return heading;
    }

    public Cog compile(){
        return new Cog(this, tuples.stream());
    }

    @Override
    public long count() {
        return tuples.size();
    }

    public int hashCode(){
        return tuples.hashCode();
    }

    public boolean equals(Relation other) {
        if (!(other instanceof SetMemoryRelation))
            other = other.stream().collect(SetMemoryRelation.collector());
        return equals((SetMemoryRelation)other);
    }

    public boolean equals(SetMemoryRelation other) {
        return tuples.equals(other.tuples);
    }

    public static Collector<Tuple, ?, Relation> collector() {
        Function<Set<Tuple>, Relation> finisher = SetMemoryRelation::new;
        return Collector.of(
                CollectionUtils::newConcurrentHashSet,
                Set<Tuple>::add,
                (left, right) -> { left.addAll(right); return left; },
                finisher,
                Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED);
    }

}
