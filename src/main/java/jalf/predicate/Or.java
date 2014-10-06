package jalf.predicate;

import static jalf.util.CollectionUtils.listOf;
import jalf.AttrList;
import jalf.Predicate;
import jalf.Tuple;
import jalf.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Or extends Unctive {

    public Or(List<Predicate> predicates) {
        super(predicates);
    }

    public Or(Predicate...predicates) {
        this(listOf(predicates));
    }

    @Override
    public boolean test(Tuple t) {
        return predicates.stream().anyMatch(p -> p.test(t));
    }

    @Override
    protected Predicate factor(List<Predicate> predicates) {
        return new Or(predicates);
    }

    public Predicate negate() {
        List<Predicate> negated = predicates.stream()
                .map(p -> p.not())
                .collect(Collectors.toList());
        return new And(negated);
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        AttrList references = getReferencedAttributes();
        AttrList invalidOnLeft = references.difference(list);
        if (invalidOnLeft.isEmpty()) {
            return new Pair<>(this, TRUE);
        } else {
            return new Pair<>(TRUE, this);
        }
    }

    @Override
    public Predicate or(Predicate other) {
        List<Predicate> newList = new ArrayList<>(this.predicates);
        if (other instanceof Or) {
            newList.addAll(((Or)other).predicates);
        } else {
            newList.add(other);
        }
        return new Or(newList);
    }

}
