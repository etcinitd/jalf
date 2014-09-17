package jalf.relation.materialized;

import jalf.Relation;
import jalf.Tuple;
import jalf.compiler.Cog;
import jalf.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;

import static jalf.util.CollectionUtils.setOf;

/**
 * MemoryRelation where an actual set of tuples is used as internal
 * representation.
 */
public class SetMemoryRelation extends MemoryRelation {
    private Collection<Tuple> tuples;

    public SetMemoryRelation(Set<Tuple> tuples) {
        this.tuples = tuples;
    }

    public SetMemoryRelation(Tuple[] tuples) {
        this.tuples = setOf(tuples);
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
