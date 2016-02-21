package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;

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

    @Override
    public Integer finish() {
        return this.state;
    }

    @Override
    public AttrName getAggregatedField() {
        return null;
    }

    @Override
    public Count duplicate() {
        return new Count();
    }

    @Override
    public boolean notAllowedAggrAttr(Type<?> t) {
        return false;
    }

    @Override
    public Type<?> getTypeOfOn() {
        return Type.scalarType(Integer.class);
    }

    @Override
    public void setTypeOfOn(Type<?> t) {
        // TODO nothing to do
    }
}
