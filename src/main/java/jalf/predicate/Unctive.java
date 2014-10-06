package jalf.predicate;

import static java.util.Collections.unmodifiableList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Unctive extends Predicate {

    protected List<Predicate> predicates;

    public Unctive(List<Predicate> predicates) {
        this.predicates = unmodifiableList(predicates);
    }

    @Override
    public void fillReferencedAttributes(Set<AttrName> attrNames) {
        for (Predicate p: predicates) {
            p.fillReferencedAttributes(attrNames);
        }
    }

    @Override
    public Predicate rename(Renaming renaming) {
        List<Predicate> renamed = predicates.stream()
            .map(p -> p.rename(renaming))
            .collect(Collectors.toList());
        return factor(renamed);
    }

    protected abstract Predicate factor(List<Predicate> predicates);

    @Override
    public int hashCode() {
        return 31*predicates.hashCode() + getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(this.getClass().equals(obj.getClass())))
            return false;
        Unctive other = (Unctive) obj;
        return predicates.equals(other.predicates);
    }

    @Override
    public String toString() {
        String sep = " " + getClass().getSimpleName().toLowerCase() + " ";
        return predicates.stream()
                .map(Object::toString)
                .collect(Collectors.joining(sep));
    }

}
