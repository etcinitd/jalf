package jalf.relation.algebra;

import jalf.Relation;

/**
 * Specialization of Operator for operators with only one relational operand.
 */
public abstract class UnaryOperator extends Operator {

    public abstract Relation getOperand();

}
