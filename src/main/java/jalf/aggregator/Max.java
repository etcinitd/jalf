package jalf.aggregator;
import jalf.AttrName;
import jalf.Tuple;

public class Max  implements Aggregator<Comparable<?>>{
    private AttrName aggregatedField;
    private Comparable<?> state;

    public static Max max(AttrName attr) {
        return new Max(attr);
    }

    public Max(AttrName aggregatedField) {
        super();
        this.aggregatedField= aggregatedField;
        this.init();
    }

    @Override
    public void init() {
        this.state=null;
    }

    @Override
    public void accumulate(Tuple t) {
        Comparable<?> value= (Comparable<?>) t.get(this.getAggregatedField());
        if (this.state==null){
            this.state= value;
        }else{
            if(CompareTo(value,this.state)>=1){
                this.state=value;
            }
        }
    }

    private int CompareTo(Comparable<?> value, Comparable<?> state) {
        if (state instanceof Integer){
            return Integer.compare((Integer)value, (Integer)state);
        }
        else if(state instanceof Double){
            return Double.compare((Double)value, (Double)state);
        }
        else if(state instanceof Long){
            return Long.compare((Long)value, (Long)state);
        }
        else if(state instanceof String){
            return value.toString().compareTo(state.toString());
        }
        return 0;
    }

    public Max conclude(Max other) {
        this.state =  other.state;
        return this;
    }

    @Override
    public AttrName getAggregatedField() {
        return this.aggregatedField;
    }

    @Override
    public Comparable<?> finish() {
        return this.state;
    }

}
