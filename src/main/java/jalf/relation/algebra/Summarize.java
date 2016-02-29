package jalf.relation.algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Relation;
import jalf.Tuple;
import jalf.Visitor;
import jalf.aggregator.Aggregator;
import jalf.type.RelationType;


public class Summarize extends UnaryOperator {

    private final Relation operand;

    private final AttrList by;

    private final RelationType type;

    private final Aggregator <?>aggregator;

    private final AttrName as;



    public Summarize(Relation operand, AttrList by,  Aggregator<?> agg, AttrName as) {
        this.operand = operand;
        this.by = by;
        this.aggregator = agg;
        this.as=as;
        this.type = typeCheck();

    }

    public List<Tuple> apply(Stream<Tuple> tuples) {

        List<Tuple> list = new ArrayList<Tuple>();
        Map<List<Object>,  ? extends Aggregator<?>> map=null;

        Supplier<Aggregator<?>> s = () -> ((Aggregator<?>) this.aggregator).duplicate();

        BiConsumer<Aggregator<?>, Tuple> b = new BiConsumer<Aggregator<?>,Tuple>(){
            @Override
            public void accept(Aggregator<?> agg, Tuple t) {
                agg.accumulate(t);
            }
        };

        java.util.function.BinaryOperator<Aggregator<?>> f = new java.util.function.BinaryOperator<Aggregator<?>>(){
            @Override
            public Aggregator<?> apply(Aggregator<?> t, Aggregator<?> u) {
                return t;
            }
        };

        Collector<Tuple, Aggregator<?>, Aggregator<?>> coll = Collector.of(s, b, f);
        Function<Tuple,List<Object>> grouper = t -> t.fetch(this.by);
        map = tuples.collect(Collectors.groupingBy(grouper, coll));



        for (Entry<List<Object>, ? extends Aggregator<?>> item : map.entrySet()) {
            list.add(Tuple.dress(computeKeyValuePairOfTuple(this.as,item, this.by)));
        }
        return list;
    }

    /**
     * Compute the key value pair list to form a tuple
     *
     * @paramAttrName as
     * @param  AttrList Entry<List<Object>,Aggregator> item
     * @param byNameAttrs
     * @return
     */
    private  Object[] computeKeyValuePairOfTuple(AttrName asName, Entry<List<Object>, ? extends Aggregator<?>> item,  AttrList byNameAttrs){
        List<Object> list = new ArrayList<Object>();
        List<AttrName> attrs = byNameAttrs.toList();
        for(int i = 0; i < attrs.size(); i++){
            list.add(attrs.get(i).getName());
            list.add((item.getKey().get(i)));
        }
        Aggregator<?> value = item.getValue();
        list.add(asName);
        list.add(value.finish());

        return list.stream().toArray();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().summarize(by, aggregator, as);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public AttrList getBy() {
        return by;
    }
    public AttrName getAs() {
        return as;
    }

    public Aggregator<?> getAggregator() {
        return aggregator;
    }


    @Override
    public List<Object> getArguments() {
        return Arrays.asList(by);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

}
