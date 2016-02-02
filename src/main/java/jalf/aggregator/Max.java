package jalf.aggregator;

import jalf.AttrList;
import jalf.Tuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Max extends Aggregator{

    private Expression exp;

    public Max(Expression exp){
        this.exp = exp;
    }

    @Override
    List<Tuple> test(Stream<Tuple> tuples, AttrList  byNameAttrs) {

        List<Tuple> list = new ArrayList<Tuple>();

        Map<Object,Optional<Tuple>> map = tuples.
                collect(Collectors.
                        groupingBy(t -> t.fetch(byNameAttrs),
                                Collectors.mapping(
                                        t -> t,
                                        Collectors.minBy(new Comparator<Object>() {
                                            @Override
                                            public int compare(Object t1, Object t2) {
                                                return -1;
                                            }
                                        }))));


        for (Map.Entry<Object,Optional<Tuple>> item : map.entrySet()) {
            System.out.println(item.getValue());
        }

        return null;

    }


}
