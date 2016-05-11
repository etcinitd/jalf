package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;
import jalf.type.RelationType;

public interface Aggregator<T> {
    public void init();
    public void accumulate(Tuple t);
    public T finish();
    public AttrName getAggregatedField();
    public Aggregator<T> duplicate();
    public Type<?> getResultingType(RelationType type);
}
