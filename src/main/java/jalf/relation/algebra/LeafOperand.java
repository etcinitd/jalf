package jalf.relation.algebra;

import jalf.Relation;
import jalf.compiler.Compiler;
import jalf.compiler.Cog;

/**
 * Extension of the Relation contract for all but algebra operators.
 *
 * This extension MUST be implemented by all relations that are not algebra
 * operators. It is part of JAlf extension API and allows contributions to
 * implement the Relation contract while allowing the compiler and optimizer
 * to do their job without compromising weak coupling.
 */
public interface LeafOperand extends Relation {

    /**
     * Compiles the leaf operand as a Cog.
     *
     * @return a Cog instance.
     */
    Cog toCog(Compiler compiler);

}
