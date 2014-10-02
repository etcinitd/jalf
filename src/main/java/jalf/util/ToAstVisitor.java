package jalf.util;

import jalf.relation.algebra.BinaryOperator;
import jalf.relation.algebra.Dee;
import jalf.relation.algebra.Dum;
import jalf.relation.algebra.LeafOperand;
import jalf.relation.algebra.UnaryOperator;

import java.util.Arrays;
import java.util.List;

public class ToAstVisitor extends DefaultVisitor<List<Object>> {

    @Override
    public List<Object> visit(UnaryOperator relation) {
        return Arrays.asList(
                relation.getClass().getSimpleName().toLowerCase(),
                relation.getOperand().accept(this),
                relation.getArguments());
    }

    @Override
    public List<Object> visit(BinaryOperator relation) {
        return Arrays.asList(
                relation.getClass().getSimpleName().toLowerCase(),
                relation.getLeft().accept(this),
                relation.getRight().accept(this),
                relation.getArguments());
    }

    @Override
    public List<Object> visit(LeafOperand relation) {
        return Arrays.asList("leaf", relation);
    }

    @Override
    public List<Object> visit(Dee dee) {
        return Arrays.asList("DEE");
    }

    @Override
    public List<Object> visit(Dum dum) {
        return Arrays.asList("DUM");
    }

}
