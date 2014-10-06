package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;

import jalf.Relation;

/**
 * Specialization of Operator for operators with only one relational operand.
 */
public abstract class UnaryOperator extends Operator {

    public abstract Relation getOperand();

    @Override
    public List<Relation> getOperands() {
        return Arrays.asList(getOperand());
    }

}
