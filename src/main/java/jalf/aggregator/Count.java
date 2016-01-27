package jalf.aggregator;

import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;
import jalf.type.RelationType;

public class Count  implements Aggregator<Long>{
    private Long state;

    public static Count count() {
        return new Count();
    }

    public Count() {
        super();
        this.init();
    }

    @Override
    public final void init() {
        this.state=new Long(0);
    }

    @Override
    public void accumulate(Tuple t) {
        this.state=this.state+1;
    }

    @Override
    public Long finish() {
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
    public Type<?> getResultingType(RelationType type) {
        return Type.scalarType(Long.class);
    }

}
