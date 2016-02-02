package jalf.aggregator;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

public abstract class Aggregator{

    public List<Tuple> apply(Stream<Tuple> tuples, AttrList  byNameAttrs){
        return test(tuples, byNameAttrs);
    }

    /**
     * This methode compute the aggregation.
     *
     * @param tuples
     * @param byNameAttrs
     * @return
     */
    abstract List<Tuple> test(Stream<Tuple> tuples, AttrList  byNameAttrs);

    public static Count count(){
        return new Count();
    }

    public static Max max(Expression exp){
        return new Max(exp);
    }

    /**
     * Compute the key value pair list to form a tuple
     *
     * @param item
     * @param tuples
     * @param byNameAttrs
     * @return
     */
    Object[] computeKeyValuePairOfTuple(String newFieldName, Entry<List<Object>,Long> item, Stream<Tuple> tuples, AttrList byNameAttrs){
        List<Object> list = new ArrayList<Object>();
        List<Object> key = item.getKey();
        List<AttrName> attrs = byNameAttrs.toList();

        for (int i = 0; i < key.size(); i++) {
            list.add(attrs.get(i).getName());
            list.add(key.get(i));
        }
        list.add(AttrName.attr(newFieldName));
        list.add(item.getValue());

        return list.stream().toArray();
    }


}
