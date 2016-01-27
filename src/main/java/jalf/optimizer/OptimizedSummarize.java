package jalf.optimizer;
import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.relation.algebra.Summarize;

public class OptimizedSummarize extends Optimized<Summarize>{

    public OptimizedSummarize(Optimizer optimizer, Summarize operator) {
        super(optimizer, operator);
    }

    /**
     * project(summarize(r, ...), a) => project(summarize(r,...), a)
     */
    @Override
    public Relation project(AttrList attributes) {
        Relation r = operator.getOperand();
        if (!attributes.contains(operator.getAs())){
            r = optimized(r).project(attributes);
        } else{
            r = optimized(r).summarize(operator.getBy(), operator.getAggregator(), operator.getAs());
            r = r.project(attributes);
        }
        return r;
    }

    /**
     * restrict(summarize(r, ...), p) => summarize(restrict(r, p), ...)
     */
    @Override
    public Relation restrict(Predicate pred) {
        Relation r = operator.getOperand();
        AttrList predattrs = pred.getReferencedAttributes();
        if (!predattrs.contains(operator.getAs())){
            r = optimized(r).restrict(pred);
            r = r.summarize(operator.getBy(), operator.getAggregator(), operator.getAs());
        } else{
            r = optimized(r).summarize(operator.getBy(), operator.getAggregator(), operator.getAs());
            r = r.restrict(pred);
        }
        return r;
    }

}
