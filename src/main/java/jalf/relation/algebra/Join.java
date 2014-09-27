package jalf.relation.algebra;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.type.RelationType;

/**
 * Relational natural join based on same name attributes.
 */
public class Join extends BinaryOperator {

    private final Relation left;

    private final Relation right;

    private final RelationType type;

    public Join(RelationType type, Relation left, Relation right) {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    public Join(Relation left, Relation right){
        this.left = left;
        this.right = right;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    public Relation getLeft() {
        return left;
    }

    @Override
    public Relation getRight() {
        return right;
    }

    /**
     * Return the attributes on which the join operates. As Join is a natural
     * join, these are the attributes common to right and left operands.
     *
     * @return the common attributes as an AttrList instance.
     */
    public AttrList getJoinAttrList() {
        AttrList leftAttrs = left.getType().toAttrList();
        AttrList rightAttrs = right.getType().toAttrList();
        return leftAttrs.intersect(rightAttrs);
    }

    @Override
    protected RelationType typeCheck() {
        return left.getType().join(right.getType());
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

}
