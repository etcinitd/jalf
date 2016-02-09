package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;

public class Count  implements Aggregator<Count>{
    private Number state;

    public static Count count() {
        return new Count();
    }


    public Count() {
        super();
        this.init();
    }



    @Override
    public Number getState() {
        return this.state;
    }


    @Override
    public void init() {
        this.state=new Integer(0);
    }


    @Override
    public void accumulate(Tuple t) {
        this.state=this.state.intValue()+1;

    }



    @Override
    public Count finish(Count other) {
        this.state =other.state;
        return this;
    }

    public static Count count(AttrName attr) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public AttrName getAggregatedField() {
        return null;
    }

}
