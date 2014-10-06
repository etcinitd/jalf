package jalf.predicate;

import static jalf.util.CollectionUtils.listOf;
import jalf.AttrList;
import jalf.Predicate;
import jalf.Tuple;
import jalf.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class And extends Unctive {

    public And(List<Predicate> predicates) {
        super(predicates);
    }

    public And(Predicate...predicates) {
        this(listOf(predicates));
    }

    @Override
    public boolean test(Tuple t) {
        return predicates.stream().allMatch(p -> p.test(t));
    }

    protected Predicate factor(List<Predicate> predicates) {
        return new And(predicates);
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        Pair<Predicate> identity = new Pair<>(TRUE, TRUE);
        Function<Predicate, Pair<Predicate>> mapper = p -> {
            return p.split(list);
        };
        BinaryOperator<Pair<Predicate>> reducer = (p1, p2) -> {
            Predicate left = p1.left.and(p2.left);
            Predicate right = p1.right.and(p2.right);
            return new Pair<Predicate>(left, right);
        };
        return predicates.stream().collect(
                Collectors.reducing(identity, mapper, reducer));
    }

    @Override
    public Predicate and(Predicate other) {
        List<Predicate> newList = new ArrayList<>(this.predicates);
        if (other instanceof And) {
            newList.addAll(((And)other).predicates);
        } else {
            newList.add(other);
        }
        return new And(newList);
    }

}
