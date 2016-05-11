package jalf.relation.materialized;

import static jalf.util.CollectionUtils.setOf;
import jalf.Relation;
import jalf.Tuple;
import jalf.TypeException;
import jalf.Visitor;
import jalf.compiler.BaseCog;
import jalf.compiler.Cog;
import jalf.compiler.Compiler;
import jalf.constraint.Key;
import jalf.constraint.Keys;
import jalf.type.RelationType;
import jalf.type.TupleType;
import jalf.util.CollectionUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * MemoryRelation where an actual set of tuples is used as internal
 * representation.
 */
public class SetMemoryRelation extends MemoryRelation {

    private RelationType type;

    private Collection<Tuple> tuples;

    //ici on précise la clef
    public SetMemoryRelation(RelationType type, Set<Tuple> tuples, Keys keys) {
        this.type = type;
        this.tuples = tuples;
        this.keys = keys;
        if (!keys.check(this))
            throw new IllegalArgumentException("Invalid key!");
    }

    //no key préciser tout les attributs deviennent la clef  de la relation
    public SetMemoryRelation(RelationType type, Set<Tuple> tuples) {
        this(type, tuples, new Keys(new Key(type.getHeading().toAttrList())));
    }

    public SetMemoryRelation(RelationType type, Tuple[] tuples) {
        this(type, setOf(tuples));
    }

    public SetMemoryRelation(RelationType type, Tuple[] tuples, Keys keys) {
        this(type, setOf(tuples), keys);
    }

    /**
     * Builds an in-memory relation of a given type, with a list of tuples as
     * body.
     *
     * @pre all tuples must have a type compatible with the specified relation
     * type. A TypeException will be thrown otherwise.
     * @pre all tuples should be distinct. Only distinct tuples will be
     * (silently) taken into account otherwise.
     * @param type the type of the relation to build.
     * @param tuples the body of the relation as a set of tuples.
     * @return the built Relation.
     */
    public static Relation tuples(RelationType type, Tuple...tuples) {
        TupleType ttype = type.toTupleType();
        Optional<Tuple> fail = Stream.of(tuples)
                .filter(t -> !ttype.contains(t))
                .findAny();
        if (fail.isPresent()) {
            Tuple t = fail.get();
            throw new TypeException("Relation type mismatch: " + t);
        } else {
            return new SetMemoryRelation(type, tuples);
        }
    }

    public static Relation tuples(RelationType type, Keys keys, Tuple...tuples) {
        TupleType ttype = type.toTupleType();
        Optional<Tuple> fail = Stream.of(tuples)
                .filter(t -> !ttype.contains(t))
                .findAny();
        if (fail.isPresent()) {
            Tuple t = fail.get();
            throw new TypeException("Relation type mismatch: " + t);
        } else {
            return new SetMemoryRelation(type, tuples, keys);
        }
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    public Cog toCog(Compiler compiler) {
        return new BaseCog(this, () -> tuples.stream());
    }

    @Override
    public long cardinality() {
        return tuples.size();
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int hashCode(){
        return tuples.hashCode();
    }

    @Override
    public boolean equals(Relation other) {
        if (!(other instanceof SetMemoryRelation))
            other = other.stream().collect(SetMemoryRelation.collector(other.getType()));
        return equals((SetMemoryRelation)other);
    }

    public boolean equals(SetMemoryRelation other) {
        return tuples.equals(other.tuples);
    }

    public static Collector<Tuple, ?, Relation> collector() {
        return collector(null);
    }

    public static Collector<Tuple, ?, Relation> collector(RelationType type) {
        Function<Set<Tuple>, Relation> finisher = (tuples) -> {
            if (type == null) {
                RelationType type2 = RelationType.infer(tuples.stream());
                return new SetMemoryRelation(type2, tuples);
            } else {
                return new SetMemoryRelation(type, tuples);
            }
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
