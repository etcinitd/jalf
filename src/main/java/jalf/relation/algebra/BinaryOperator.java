package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;

import jalf.Relation;

/**
 * Specialization of Operator for operators with two relational operands.
 */
public abstract class BinaryOperator extends Operator {

    public abstract Relation getLeft();

    public abstract Relation getRight();

    @Override
    public List<Relation> getOperands() {
        return Arrays.asList(getLeft(), getRight());
    }

}
