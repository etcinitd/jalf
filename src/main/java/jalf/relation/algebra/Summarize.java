package jalf.relation.algebra;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Relation;
import jalf.Visitor;
import jalf.aggregator.Aggregator;
import jalf.type.RelationType;

import java.util.Arrays;
import java.util.List;

public class Summarize extends UnaryOperator {

    private final Relation operand;

    private final AttrList attributes;

    private final RelationType type;

    private final Aggregator aggregator;


    public Summarize(Relation operand, AttrList attributes, RelationType type, Aggregator aggregator) {
        this.operand = operand;
        this.attributes = attributes;
        this.aggregator = aggregator;
        this.type = type;
    }

    public Summarize(Relation operand, AttrList attributes, AttrName newname, Aggregator aggregator) {
        this.operand = operand;
        this.attributes = attributes;
        this.aggregator = aggregator;
        this.type = typeCheck();
    }

    public Summarize(Relation operand, AttrList attributes, Aggregator aggregator) {
        this.operand = operand;
        this.attributes = attributes;
        this.aggregator = aggregator;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().summarize(attributes);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public AttrList getAttributes() {
        return attributes;
    }

    public Aggregator getAggregator() {
        return aggregator;
    }


    @Override
    public List<Object> getArguments() {
        return Arrays.asList(attributes);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }


}
