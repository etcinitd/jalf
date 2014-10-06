package jalf;

import java.util.function.Function;

import jalf.relation.algebra.*;

public interface Visitor<R> extends Function<Relation,R> {

    default R apply(Relation r) {
        return r.accept(this);
    }

    ///

    public R visit(Project relation);

    public R visit(Rename relation);

    public R visit(Restrict relation);

    ///

    public R visit(Join relation);

    ///

    public R visit(LeafOperand relation);

    ///

    public R visit(Dee dee);

    public R visit(Dum dum);

}
