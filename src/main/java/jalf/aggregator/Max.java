package jalf.aggregator;
import jalf.AttrName;
import jalf.Tuple;

public class Max extends Aggregator{




    public Max(AttrName aggregatedField) {
        super();
        this.setAggregatedField(aggregatedField);
        this.initState();
    }


    @Override
    public void initState() {


    }

    @Override
    public Aggregator finishState( Aggregator other) {
        this.state = other.state;
        return this;
    }



    @Override
    public void updateState(Tuple t) {
        // TODO Auto-generated method stub

    }


    @Override
    public Number getState() {
        // TODO Auto-generated method stub
        return null;
    }


}
