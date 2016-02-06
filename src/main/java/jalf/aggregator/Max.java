package jalf.aggregator;
import jalf.AttrName;
import jalf.Tuple;

public class Max extends Aggregator{

    public Max() {
        super();
        this.initState();
    }



    public Max(AttrName aggregatedField) {
        super();
        this.setAggregatedField(aggregatedField);
        this.initState();
    }


    @Override
    public void initState() {
        this.state=new Integer(0);


    }

    @Override
    public Aggregator finishState( Aggregator other) {
        this.state = other.state;
        return this;
    }



    @Override
    public void updateState(Tuple t) {

        Integer value=(Integer) t.get(this.getAggregatedField());
        if(value > this.state.intValue()){
            this.state=value;
        }




    }


    @Override
    public Number getState() {
        return this.state;
    }


}
