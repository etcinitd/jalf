package jalf.relation.algebra;

import jalf.compiler.AbstractRelation;
import jalf.Tuple;

import java.util.stream.Stream;

/**
 * Parent (abstract) class for all relations captured by lazy-evaluated
 * algebraic expressions.
 */
public abstract class Operator extends AbstractRelation {

    @Override
    public Stream<Tuple> stream() {
        // TODO implement
        return null;
    }

}
