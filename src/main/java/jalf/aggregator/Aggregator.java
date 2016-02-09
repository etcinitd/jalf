package jalf.aggregator;

import jalf.Tuple;

public interface Aggregator<T> {

    void init();
    void accumulate(Tuple t);
    public T finish();
}