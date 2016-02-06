package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;

public abstract class Aggregator{
    private AttrName aggregatedField;
    protected  Number state;
    protected  Integer counttuple ;



    public Aggregator() {

    }

    public Aggregator( AttrName aggregatedField) {
        this.aggregatedField= aggregatedField;
    }





    public static Count count(){
        return new Count();
    }

    public static Max max(AttrName aggregatedField){

        return new Max(aggregatedField);
    }

    public static Avg avg(AttrName aggregatedField){

        return new Avg(aggregatedField);
    }

    public AttrName getAggregatedField() {
        return aggregatedField;
    }



    public void setAggregatedField(AttrName aggregatedField) {
        this.aggregatedField = aggregatedField;
    }

    abstract public void initState();

    abstract public Aggregator finishState(Aggregator other) ;

    abstract public void updateState(Tuple t) ;

    abstract public Number getState();




}
