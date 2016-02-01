package jalf.relation.algebra;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Relation;
import jalf.Visitor;
import jalf.aggregator.Aggregator;
import jalf.type.RelationType;

public class Summarize extends UnaryOperator {

    private final Relation operand;

    private final AttrList attributes;

    private final RelationType type;

    private final Set<Aggregator> aggregators;


    public Summarize(Relation operand, AttrList attributes, RelationType type,Set<Aggregator> aggregators) {
        this.operand = operand;
        this.attributes = attributes;
        this.aggregators=aggregators;
        this.type = type;
    }

    public Summarize(Relation operand, AttrList attributes, AttrName newname, Set<Aggregator> aggregators) {
        this.operand = operand;
        this.attributes = attributes;
        this.aggregators=aggregators;
        this.type = typeCheck();
    }

    public Summarize(Relation operand, AttrList attributes, Aggregator aggregator) {
        this.operand = operand;
        this.attributes = attributes;
        this.aggregators=new HashSet<Aggregator>();
        this.aggregators.add(aggregator);
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

    public Set<Aggregator> getAggregators() {
        return aggregators;
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
