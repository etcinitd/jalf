package jalf.relation.algebra;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jalf.AttrList;
import jalf.Relation;
import jalf.Visitor;
import jalf.constraint.Key;
import jalf.constraint.Keys;
import jalf.type.RelationType;

/**
 * Relational projection.
 */
public class Project extends UnaryOperator {

    private final Relation operand;

    private final AttrList attributes;

    private final RelationType type;

    public Project(Relation operand, AttrList attributes, RelationType type) {
        this.operand = operand;
        this.attributes = attributes;
        this.type = type;
    }

    public Project(Relation operand, AttrList attributes) {
        this.operand = operand;
        this.attributes = attributes;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().project(attributes);
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    public AttrList getAttributes() {
        return attributes;
    }

    @Override
    public List<Object> getArguments() {
        return Arrays.asList(attributes);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }


    /**
     * we look for keys that are preserved by the projection and
     * if so these keys are the new keys, otherwise the 'on' attributes
     * form the new key (key of the projection)
     */
    @Override
    public Keys lazyComputeKey(){
        Keys keys = operand.getKeys();
        Predicate<Key> p = (k)-> isKeyPreserving(k);
        Set<Key> kept = keys.stream().filter(p).collect(Collectors.toSet());
        if (kept.isEmpty()) {
            return new Keys(type.getLargestKey());
        } else{
            return new Keys(kept);
        }
    }

    public boolean isKeyPreserving() {
        Predicate<Key> pred = (k)-> isKeyPreserving(k);
        return operand.getKeys().stream().anyMatch(pred);
    }

    public boolean isKeyPreserving(Key key){
        return key.isSubSetOf(this.attributes);
    }

}
