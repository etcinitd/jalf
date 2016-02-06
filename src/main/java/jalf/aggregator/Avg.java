package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;

public class Avg extends Aggregator{


    public Avg(AttrName aggregatedField) {
        super();
        this.setAggregatedField(aggregatedField);
        this.initState();
    }

    @Override
    public void initState() {
        this.state=new Double(0);
        this.counttuple=new Integer(0);

    }

    @Override
    public Aggregator finishState(Aggregator other) {
        this.counttuple=other.counttuple;
        this.state = other.state;
        System.out.println(this.counttuple);

        return this;
    }

    @Override
    public void updateState(Tuple t) {
        Integer value=(Integer) t.get(this.getAggregatedField());

        this.state= this.state.doubleValue() +value;
        this.counttuple=this.counttuple+1;
        // System.out.println(this.counttuple);


    }

    @Override
    public Number getState() {
        if (this.counttuple > 0 ){
            this.state =this.state.doubleValue()/this.counttuple.doubleValue();
        }
        else {
            this.state=0;
        }
        return this.state;
    }


}
