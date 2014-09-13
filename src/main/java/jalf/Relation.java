package jalf;

import java.util.Collection;

import static jalf.util.Utils.listOf;

/**
 * @author amirm
 */
public class Relation {
    private Collection<Tuple> tuples;

    // TODO What about passing a Stream?
    public Relation(Tuple... tuples) {
        this.tuples = listOf(tuples);
    }

    public long count() {
        return tuples.size();
    }
}
