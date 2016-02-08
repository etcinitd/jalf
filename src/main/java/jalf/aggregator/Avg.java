package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;

public class Avg implements Aggregator<Avg>{
    private AttrName aggregatedField;
    private  Number state;
    private  Integer counttuple ;



    public static Avg avg(AttrName attr) {
        return new Avg(attr);
    }


    public Avg(AttrName aggregatedField) {
        super();
        this.setAggregatedField(aggregatedField);
        this.init();
    }



    public void setAggregatedField(AttrName aggregatedField) {
        this.aggregatedField= aggregatedField;
    }




    @Override
    public Number getState() {
        if (this.counttuple > 0 ){
            this.state =this.state.doubleValue()/this.counttuple;
        }
        else {
            this.state=0;
        }
        return this.state;
    }

    @Override
    public void init() {
        this.state=new Double(0);
        this.counttuple=new Integer(0);

    }

    @Override
    public void accumulate(Tuple t) {
        System.out.println(t.get(this.getAggregatedField()).getClass());
        Integer value=(Integer) t.get(this.getAggregatedField());
        this.state= this.state.doubleValue() +value;
        this.counttuple=this.counttuple+1;

    }

    @Override
    public AttrName getAggregatedField() {
        return this.aggregatedField;
    }



    @Override
    public Avg finish(Avg other) {
        this.counttuple=other.counttuple;
        this.state = other.state;
        return this;
    }

}
