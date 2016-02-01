package jalf.relation.algebra;

import java.util.List;
import java.util.Set;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.aggregator.Aggregator;
import jalf.type.RelationType;

public class Summarize extends UnaryOperator {
    private final Relation operand;

    private final AttrList attributes;

    private final RelationType type;

    private final  Set<Aggregator> aggregators;


    @Override
    public RelationType getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Relation getOperand() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected RelationType typeCheck() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> getArguments() {
        // TODO Auto-generated method stub
        return null;
    }

}
