package jalf.aggregator;
import java.util.Comparator;

import jalf.AttrName;
import jalf.Tuple;

public class Max  implements Aggregator<Max>,Comparator<Number>{


    private AttrName aggregatedField;
    private  Number state;


    public static Max max(AttrName attr) {
        return new Max(attr);
    }

    public Max(AttrName aggregatedField) {
        super();
        this.setAggregatedField(aggregatedField);
        this.init();
    }


    public void setAggregatedField(AttrName aggregatedField) {
        this.aggregatedField= aggregatedField;
    }

    @Override
    public Number getState() {
        return this.state;
    }

    @Override
    public int compare(Number n1, Number n2) {
        long l1 = n1.longValue();
        long l2 = n2.longValue();
        if (l1 != l2)
            return (l1 < l2 ? -1 : 1);
        return Double.compare(n1.doubleValue(), n2.doubleValue());
    }


    @Override
    public void init() {
        this.state=null;

    }


    @Override
    public void accumulate(Tuple t) {
        Number value= (Number) t.get(this.getAggregatedField());
        if (this.state==null){
            this.state= value;
        }else{
            if(compare(value,this.state)==1){
                this.state=value;
            }

        }
    }

    @Override
    public AttrName getAggregatedField() {
        return this.aggregatedField;
    }

    @Override
    public Max finish(Max other) {
        this.state = other.state;
        return this;
    }



}
