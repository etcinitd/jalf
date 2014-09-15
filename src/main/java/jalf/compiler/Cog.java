package jalf.compiler;

import jalf.Relation;
import jalf.Tuple;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;

import java.util.stream.Stream;

/**
 * Compiled-version of a relation(al) expression, ready to be consumed.
 *
 * Cogs are the intermediate language used by JAlf when compiling relational
 * expressions. It is a middleware between logic expressions and streams
 * providing access to the list of tuples of a relation. Cogs implement stream
 * manipulation algorithms while also providing the compilation context in
 * terms of the source expression and actual compiler used.
 *
 * This parent class implements the default compilation mechanism using Java
 * streams. It is intended to be subclassed for specific compilation schemes
 * (e.g. SQL compilation), specific optimizations and/or algorithms.
 */
public class Cog {

    private Relation expr;

    private Stream<Tuple> stream;

    public Cog(Relation expr, Stream<Tuple> stream) {
        this.expr = expr;
        this.stream = stream;
    }

    public Relation getExpr() {
        return expr;
    }

    public Stream<Tuple> stream() {
        return stream;
    }

    /** Default compilation of `project`. */
    public Cog project(Project projection, Cog compiled){
        Stream<Tuple> stream = compiled.stream()
                .map(t -> t.project(projection.getAttributes()))
                .distinct();
        return new Cog(projection, stream);
    }

    /** Default compilation of `rename`. */
    public Cog rename(Rename rename, Cog compiled){
        Stream<Tuple> stream = compiled.stream()
                .map(t -> t.rename(rename.getRenaming()));
        return new Cog(rename, stream);
    }

}