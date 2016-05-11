package jalf.util;

import jalf.Visitor;
import jalf.relation.algebra.BinaryOperator;
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
import jalf.relation.algebra.UnaryOperator;
import jalf.relation.algebra.Union;

public class DefaultVisitor<T> implements Visitor<T> {

    public T visit(UnaryOperator relation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T visit(Select relation) {
        return visit((UnaryOperator)relation);
    }

    @Override
    public T visit(Project relation) {
        return visit((UnaryOperator)relation);
    }
    @Override
    public T visit(Summarize relation) {
        return visit((UnaryOperator)relation);
    }

    @Override
    public T visit(Rename relation) {
        return visit((UnaryOperator)relation);
    }

    @Override
    public T visit(Restrict relation) {
        return visit((UnaryOperator)relation);
    }

    public T visit(BinaryOperator relation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T visit(Union relation) {
        return visit((BinaryOperator)relation);
    }

    @Override
    public T visit(Minus relation) {
        return visit((BinaryOperator)relation);
    }

    @Override
    public T visit(Join relation) {
        return visit((BinaryOperator)relation);
    }

    @Override
    public T visit(Intersect relation) {
        return visit((BinaryOperator)relation);
    }

    @Override
    public T visit(LeafOperand relation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T visit(Dee dee) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T visit(Dum dum) {
        throw new UnsupportedOperationException();
    }

}
