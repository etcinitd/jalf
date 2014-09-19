package jalf.relation.materialized;

import static jalf.util.CollectionUtils.setOf;
import jalf.Relation;
import jalf.Tuple;
import jalf.compiler.Cog;
import jalf.type.RelationType;
import jalf.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * MemoryRelation where an actual set of tuples is used as internal
 * representation.
 */
public class SetMemoryRelation extends MemoryRelation {
    private RelationType type;
    private Collection<Tuple> tuples;

    public SetMemoryRelation(RelationType type, Set<Tuple> tuples) {
        this.type = type;
        this.tuples = tuples;
    }

    public SetMemoryRelation(RelationType type, Tuple[] tuples) {
        this(type, setOf(tuples));
    }

    @Override
    public RelationType getType() {
        return type;
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
            other = other.stream().collect(SetMemoryRelation.collector(other.getType()));
        return equals((SetMemoryRelation)other);
    }

    public boolean equals(SetMemoryRelation other) {
        return tuples.equals(other.tuples);
    }

    public static Collector<Tuple, ?, Relation> collector(RelationType type) {
        Function<Set<Tuple>, Relation> finisher = (tuples) -> {
            return new SetMemoryRelation(type, tuples);
        };
        return Collector.of(
                CollectionUtils::newConcurrentHashSet,
                Set<Tuple>::add,
                (left, right) -> { left.addAll(right); return left; },
                finisher,
                Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED);
    }

}
