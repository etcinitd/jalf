package jalf.relation.algebra;

import jalf.AttrList;
import jalf.Relation;
import jalf.Selection;
import jalf.Visitor;
import jalf.constraint.Key;
import jalf.constraint.Keys;
import jalf.type.RelationType;

import java.util.Arrays;
import java.util.List;

public class Select extends UnaryOperator {

    private final Relation operand;

    private final Selection selection;

    private RelationType type;

    public Select(Relation operand, Selection selection, RelationType type) {
        this.operand = operand;
        this.selection = selection;
    }

    public Select(Relation operand, Selection selection) {
        this.operand = operand;
        this.selection = selection;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return selection.toRelationType();
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public Selection getSelection() {
        return selection;
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList(selection);
    }

    @Override
    protected Keys lazyComputeKey() {
        return new Keys(Key.candidate(AttrList.attrs(this.selection.gefns()
                .keySet())));
    }

}
