package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.type.RelationType;

public class Intersect extends BinaryOperator{

    private final Relation left;

    private final Relation right;

    private final RelationType type;

    public Intersect(RelationType type, Relation left, Relation right) {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    public Intersect(Relation left, Relation right){
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
     * Return the attributes on which the intersect operates.
     * @return left attributes as an AttrList instance.
     */
    public AttrList getIntersectAttrList() {
        AttrList leftAttrs = left.getType().toAttrList();
        return leftAttrs;
    }

    @Override
    protected RelationType typeCheck() {
        return left.getType().intersect(right.getType());
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList();
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

}
