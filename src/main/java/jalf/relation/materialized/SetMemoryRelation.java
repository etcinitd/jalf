package jalf.relation.materialized;

import static jalf.util.CollectionUtils.setOf;
import jalf.Relation;
import jalf.Tuple;
import jalf.compiler.Cog;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

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
        if (other instanceof SetMemoryRelation)
            return equals((SetMemoryRelation)other);
        else
            return equals(other.stream());
    }

    public boolean equals(Stream<Tuple> other) {
        return other.allMatch(t -> tuples.contains(t));
    }

    public boolean equals(SetMemoryRelation other) {
        return tuples.equals(other.tuples);
    }

    public static Collector<Tuple, ?, Relation> collector() {
        Function<HashSet<Tuple>, Relation> finisher = SetMemoryRelation::new;
        return Collector.of(
                HashSet::new,
                HashSet::add,
                (left, right) -> { left.addAll(right); return left; },
                finisher,
                Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED);
    }

}
