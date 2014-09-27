package jalf;

import jalf.relation.algebra.*;

public interface Visitor<R> {

    ///

    public R visit(Project relation);

    public R visit(Rename relation);

    public R visit(Restrict relation);

    ///

    public R visit(Join relation);

    ///

    public R visit(LeafOperand relation);

}
