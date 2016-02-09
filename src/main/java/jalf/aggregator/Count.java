package jalf.aggregator;

import jalf.Tuple;

public class Count  implements Aggregator<Integer>{
    private Integer state;

    public static Count count() {
        return new Count();
    }


    public Count() {
        super();
        this.init();
    }

    @Override
    public void init() {
        this.state=new Integer(0);
    }


    @Override
    public void accumulate(Tuple t) {
        this.state=this.state+1;

    }



    public Count conclude(Count other) {
        this.state =  other.state;
        return this;
    }
    @Override
    public Integer finish() {
        return this.state;
    }




}
