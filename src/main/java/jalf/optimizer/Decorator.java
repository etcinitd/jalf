package jalf.optimizer;

import jalf.Relation;
import jalf.Visitor;
import jalf.relation.algebra.Join;
import jalf.relation.algebra.LeafOperand;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;

/**
 * This class decorates relational operators as their optimized counterparts.
 * It implements `Visitor` as an efficient way of applying per-operator
 * delegation but is not a visitor per-se, as it does not properly visit the
 * AST.
 */
public class Decorator implements Visitor<Relation> {

    private Optimizer optimizer;

    public Decorator(Optimizer optimizer) {
        this.optimizer = optimizer;
    }

    public Relation decorate(Relation relation) {
        return relation.accept(this);
    }

    ///

    public Relation visit(Project relation) {
        return new OptimizedProject(optimizer, relation);
    }

    public Relation visit(Rename relation) {
        return new OptimizedRename(optimizer, relation);
    }

    public Relation visit(Restrict relation) {
        return new Optimized<Restrict>(optimizer, relation);
    }

    ///

    public Relation visit(Join relation) {
        return new OptimizedJoin(optimizer, relation);
    }

    ///

    public Relation visit(LeafOperand relation) {
        return new Optimized<Relation>(optimizer, relation);
    }

}
