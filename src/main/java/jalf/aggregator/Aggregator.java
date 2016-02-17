package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;

public interface Aggregator<T> {
    void init();
    void accumulate(Tuple t);
    public T finish();
    public AttrName getAggregatedField();
    public Aggregator<T> duplicate();
}