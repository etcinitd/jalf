package jalf;

import static jalf.util.CollectionUtils.rekey;
import static jalf.util.CollectionUtils.remap;
import jalf.type.Heading;
import jalf.type.RelationType;
import jalf.type.TupleType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Selection implements Function<Tuple,Tuple> {

    private Map<AttrName, SelectionMember> fns;

    public Selection(Map<AttrName, SelectionMember> fns) {
        this.fns = fns;
        tupleType = TupleType.dress(heading);
        relationType = RelationType.dress(heading);
    }

    public static Selection identity(RelationType type) {
        Map<AttrName, SelectionMember> fns = new HashMap<>();
        AttrList list = type.getHeading().toAttrList();
        list.forEach(attr -> {
            fns.put(attr, SelectionMember.attrSelection(type, attr));
        });
        return new Selection(fns);
    }

    public static Selection varargs(Object...args) {
        Map<AttrName, SelectionMember> fns = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            AttrName attr = AttrName.dress(args[i++]);
            SelectionMember member = (SelectionMember) args[i];
            fns.put(attr, member);
        }
        return new Selection(fns);
    }

    @Override
    public Tuple apply(Tuple t) {
        return new Tuple(tupleType, remap(fns, (a,fn) -> fn.apply(t)));
    }

    public Selection project(AttrList p) {
        Map<AttrName, SelectionMember> fns = new HashMap<>();
        p.forEach(a -> fns.put(a, this.fns.get(a)));
        return new Selection(fns);
    }

    public Selection rename(Renaming renaming) {
        return new Selection(rekey(fns, (a,fn) -> renaming.apply(a)));
    }

    protected Heading heading;
    protected Heading toHeading() {
        if (heading == null)
            heading = new Heading(remap(fns, (a,fn) -> fn.getType()));
        return heading;
    }

    protected TupleType tupleType;
    public TupleType toTupleType() {
        if (tupleType == null)
            tupleType = TupleType.dress(toHeading());
        return tupleType;
    }

    protected RelationType relationType;
    public RelationType toRelationType() {
        if (relationType == null)
            relationType = RelationType.dress(toHeading());
        return relationType;
    }

}
