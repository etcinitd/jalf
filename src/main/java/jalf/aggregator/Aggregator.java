package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;

public interface Aggregator<T> {

    void init();
    void accumulate(Tuple t);
    T finish(T other);
    Number getState();
    AttrName getAggregatedField();


}
