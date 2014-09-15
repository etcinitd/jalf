package jalf.relation.materialized;

import static jalf.util.CollectionUtils.setOf;
import jalf.Tuple;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * MemoryRelation where an actual set of tuples is used as internal
 * representation.
 */
public class SetMemoryRelation extends MemoryRelation {
    private Collection<Tuple> tuples;

    public SetMemoryRelation(Tuple[] tuples) {
        this.tuples = setOf(tuples);
    }

    @Override
    public Stream<Tuple> stream() {
        return tuples.stream();
    }

    @Override
    public long count() {
        return tuples.size();
    }

    public int hashCode(){
        return tuples.hashCode();
    }

    public boolean equals(Object other){
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other instanceof SetMemoryRelation)
            return tuples.equals(((SetMemoryRelation)other).tuples);
        return super.equals(other);
    }

}
