package jalf.aggregator;

import jalf.Tuple;

public class Count extends Aggregator{


    public Count() {
        super();
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
        this.state=this.state.intValue()+1;

    }


    @Override
    public Number getState() {

        return this.state;
    }






}
