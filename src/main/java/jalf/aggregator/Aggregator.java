package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;
import jalf.type.RelationType;

public interface Aggregator<T> {
    void init();
    void accumulate(Tuple t);
    public T finish();
    public AttrName getAggregatedField();
    public Aggregator<T> duplicate();
    public boolean notAllowedAggrAttr(RelationType t);
    public Type<?> getAggregatedType(RelationType type);
}