package jalf.aggregator;

import jalf.AttrList;
import jalf.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Count extends Aggregator{

    @Override
    List<Tuple> test(Stream<Tuple> tuples, AttrList byNameAttrs) {

        List<Tuple> list = new ArrayList<Tuple>();
        Map<List<Object>, Long> map = tuples.collect(Collectors.groupingBy(t -> t.fetch(byNameAttrs), Collectors.counting()));

        for (Entry<List<Object>,Long> item : map.entrySet()) {

            list.add(Tuple.dress(computeKeyValuePairOfTuple("count",item, tuples, byNameAttrs)));
        }
        return list;
    }

}
