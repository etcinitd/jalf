package jalf;

import java.util.function.Function;

import jalf.relation.algebra.*;

public interface Visitor<R> extends Function<Relation,R> {

    default R apply(Relation r) {
        return r.accept(this);
    }

    ///

    R visit(Project relation);

    R visit(Select select);

    R visit(Rename relation);

    R visit(Restrict relation);

    ///

    R visit(Join relation);
    
    R visit(Union relation);

    ///

    R visit(LeafOperand relation);

    ///

    R visit(Dee dee);

    R visit(Dum dum);

}
