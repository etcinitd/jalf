package jalf.compiler;

import jalf.Visitor;
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

/**
 * Compiles algebra expressions as Cog, taken as JAlf intermediate language.
 *
 * This class is a relation visitor that performs a depth-first visit of the
 * AST. Leaf operands compile themselves through their `toCog` method. The
 * compilation of operators is delegated to the returned cog themselves. Doing
 * so allows Cogs to implement their own compilation strategy (e.g. a Cog over
 * a SQL database will likely implement a different compilation process than an
 * in-memory relation, as most of the algebra can be translated to SQL). The Cog
 * class provides a default implementation for all operators on top of JAlf
 * stream engine. Therefore, this class itself does not encapsulate much
 * compilation logic; it mostly helps delegating in an efficient way without
 * bloating the AST classes themselves.
 */
public class Compiler implements Visitor<Cog> {

    ///

    @Override
    public Cog visit(Select relation) {
        Cog compiled = relation.getOperand().accept(this);
        return compiled.select(relation);
    }

    @Override
    public Cog visit(Project relation) {
        Cog compiled = relation.getOperand().accept(this);
        return compiled.project(relation);
    }

    @Override
    public Cog visit(Summarize relation) {
        Cog compiled = relation.getOperand().accept(this);
        return compiled.summarize(relation);
    }


    @Override
    public Cog visit(Rename relation) {
        Cog compiled = relation.getOperand().accept(this);
        return compiled.rename(relation);
    }

    @Override
    public Cog visit(Restrict relation) {
        Cog compiled = relation.getOperand().accept(this);
        return compiled.restrict(relation);
    }

    ///

    @Override
    public Cog visit(Join relation) {
        Cog left = relation.getLeft().accept(this);
        Cog right = relation.getRight().accept(this);
        return left.join(relation, right);
    }

    ///

    @Override
    public Cog visit(Union relation) {
        Cog left = relation.getLeft().accept(this);
        Cog right = relation.getRight().accept(this);
        return left.union(relation, right);
    }

    @Override
    public Cog visit(Intersect relation) {
        Cog left = relation.getLeft().accept(this);
        Cog right = relation.getRight().accept(this);
        return left.intersect(relation, right);
    }

    @Override
    public Cog visit(Minus relation) {
        Cog left = relation.getLeft().accept(this);
        Cog right = relation.getRight().accept(this);
        return left.minus(relation, right);
    }

    @Override
    public Cog visit(LeafOperand relation) {
        return relation.toCog(this);
    }

    ///

    @Override
    public Cog visit(Dee dee) {
        return dee.toCog();
    }

    @Override
    public Cog visit(Dum dum) {
        return dum.toCog();
    }

}
