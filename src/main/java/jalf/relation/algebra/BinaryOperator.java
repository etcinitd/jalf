package jalf.relation.algebra;

import jalf.Relation;

/**
 * Specialization of Operator for operators with two relational operands.
 */
public abstract class BinaryOperator extends Operator {

    public abstract Relation getLeft();

    public abstract Relation getRight();

}
