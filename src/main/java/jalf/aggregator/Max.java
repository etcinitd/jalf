package jalf.aggregator;
import jalf.AttrName;
import jalf.Tuple;
import jalf.Type;
import jalf.type.RelationType;

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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void accumulate(Tuple t) {
        Comparable value = (Comparable) t.get(this.getAggregatedField());
        if (this.state == null){
            this.state = value;
        } else {
            if (value.compareTo(this.state)>0) {
                this.state = value;
            }
        }
    }

    @Override
    public AttrName getAggregatedField() {
        return this.aggregatedField;
    }

    @Override
    public Comparable<?> finish() {
        return this.state;
    }

    @Override
    public Max duplicate() {
        return new Max(this.aggregatedField);
    }

    @Override
    public boolean notAllowedAggrAttr(RelationType type) {
        Type<?> t = type.getHeading().getTypeOf(this.aggregatedField);
        if (Comparable.class.isAssignableFrom(t.getRepresentationClass()))
            return false;
        return true;
    }

    @Override
    public Type<?> getResultingType(RelationType type) {
        return type.getTypeOf(this.aggregatedField);
    }

}
