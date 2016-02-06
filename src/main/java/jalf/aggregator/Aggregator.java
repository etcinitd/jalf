package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;

public abstract class Aggregator{
    private AttrName aggregatedField;
    protected  Number state;









    public static Count count(){
        return new Count();
    }

    public static Max max(AttrName aggregatedField){

        return new Max(aggregatedField);
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

    public Count finishState(Count other) {
        // TODO Auto-generated method stub
        return null;
    }

}
