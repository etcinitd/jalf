package jalf.relation.materialized;

import static jalf.util.CollectionUtils.setOf;
import jalf.Tuple;
import jalf.compiler.Cog;

import java.util.Collection;

/**
 * MemoryRelation where an actual set of tuples is used as internal
 * representation.
 */
public class SetMemoryRelation extends MemoryRelation {
    private Collection<Tuple> tuples;

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
