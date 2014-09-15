package jalf;

import java.util.Set;

import static jalf.util.CollectionUtils.setOf;

/**
 * @author amirm
 */
public class Relation {
    private Set<Tuple> tuples;

    // TODO What about passing a Stream?
    public Relation(Tuple... tuples) {
        this.tuples = setOf(tuples);
    }

    public long count() {
        return tuples.size();
    }

    @Override
    public boolean equals(Object r) {
        if (this == r) return true;
        if (r == null || getClass() != r.getClass()) return false;

        Relation relation = (Relation) r;

        if (!tuples.equals(relation.tuples)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tuples.hashCode();
    }
}
