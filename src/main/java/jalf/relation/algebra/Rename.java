package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;

import jalf.Relation;
import jalf.Renaming;
import jalf.Visitor;
import jalf.constraint.Keys;
import jalf.type.RelationType;

/**
 * Relational renaming.
 */
public class Rename extends UnaryOperator {

    private final Relation operand;

    private final Renaming renaming;

    private RelationType type;

    public Rename(Relation operand, Renaming renaming, RelationType type) {
        this.operand = operand;
        this.renaming = renaming;
        this.type = type;
    }

    public Rename(Relation operand, Renaming renaming) {
        this.operand = operand;
        this.renaming = renaming;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        // TODO: implement proper type checking (?)
        return operand.getType().rename(renaming);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public Renaming getRenaming() {
        return renaming;
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList(renaming);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    protected Keys lazyComputeKey() {
        return operand.getKeys().rename(renaming);
    }

}
