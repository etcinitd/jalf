package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;

public class  Avg implements Aggregator<Double>{
    private AttrName aggregatedField;
    private  Double state;
    private  Integer counttuple ;

    public static Avg avg(AttrName attr) {
        return new Avg(attr);
    }

    public Avg(AttrName aggregatedField) {
        super();
        this.aggregatedField= aggregatedField;
        this.init();
    }

    @Override
    public Double finish() {
        if (this.counttuple > 0 ){
            this.state =this.state/this.counttuple;
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
        Integer value=(Integer) t.get(this.getAggregatedField());
        this.state= this.state.doubleValue() +value;
        this.counttuple=this.counttuple+1;
    }

    @Override
    public AttrName getAggregatedField() {
        return this.aggregatedField;
    }

    @Override
    public Avg duplicate() {
        return new Avg(this.aggregatedField);
    }

    @Override
    public boolean notAllowedAggrAttr(Type<?> t) {
        Class<?> tt = null;
        //try {
        tt = t.getRepresentationClass().getSuperclass();
        if (tt == Number.class)
            return false;
        //if (tt.getClass().isInstance(Number.class))
        //    return false;
        /*} catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }*/
        return true;
    }

}
