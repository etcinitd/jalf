package jalf;

import java.util.function.Function;

import jalf.relation.algebra.Dee;
import jalf.relation.algebra.Dum;
import jalf.relation.algebra.Intersect;
import jalf.relation.algebra.Join;
import jalf.relation.algebra.LeafOperand;
import jalf.relation.algebra.Minus;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;
import jalf.relation.algebra.Select;
import jalf.relation.algebra.Summarize;
import jalf.relation.algebra.Union;

public interface Visitor<R> extends Function<Relation,R> {

    @Override
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

    R visit(Intersect relation);

    R visit(Minus relation);

    R visit(Summarize relation);

    ///

    R visit(LeafOperand relation);

    ///

    R visit(Dee dee);

    R visit(Dum dum);

}
