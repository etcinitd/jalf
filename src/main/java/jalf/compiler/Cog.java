package jalf.compiler;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.Selection;
import jalf.Tuple;
import jalf.relation.algebra.Intersect;
import jalf.relation.algebra.Join;
import jalf.relation.algebra.Minus;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;
import jalf.relation.algebra.Select;
import jalf.relation.algebra.Summarize;
import jalf.relation.algebra.Union;
import jalf.type.TupleType;

/**
 * Compiled-version of a relation(al) expression, ready to be consumed.
 *
 * Cogs are the intermediate language used by JAlf when compiling relational
 * expressions. It is a middleware between logic expressions and streams
 * providing access to the list of tuples of a relation. Cogs implement stream
 * manipulation algorithms while also providing the compilation context in
 * terms of the source expression and actual compiler used. Also, unlike
 * Java streams, Cog may be consumed multiple times, thereby providing a real
 * reusable compilation result.
 *
 * This parent class implements the default compilation mechanism using Java
 * streams. It is intended to be subclassed for specific compilation schemes
 * (e.g. SQL compilation), specific optimizations and/or algorithms.
 */
public abstract class Cog {

    protected Relation expr;

    public Cog(Relation expr) {
        super();
        this.expr = expr;
    }

    public Relation getExpr() {
        return expr;
    }

    public abstract Stream<Tuple> stream();

    public Cog select(Select relation) {
        Selection selection = relation.getSelection();

        Supplier<Stream<Tuple>> supplier = () -> this.stream()
                .map(t -> selection.apply(t))
                .distinct();

        return new BaseCog(relation, supplier);
    }

    /** Default compilation of `project`. */
    public Cog project(Project projection) {
        AttrList on = projection.getAttributes();
        TupleType tt = projection.getTupleType();

        // stream compilation: map projection + distinct
        Supplier<Stream<Tuple>> supplier = () -> this.stream()
                .map(t -> t.project(on, tt))
                .distinct();

        return new BaseCog(projection, supplier);
    }

    /** Default compilation of `summarize`. */
    public Cog summarize(Summarize summarized) {
        Supplier<Stream<Tuple>> supplier = () ->{
            Stream<Tuple> tuples = this.stream();
            List<Tuple> newTuple = summarized.apply(tuples);
            return newTuple.stream();
        };
        return new BaseCog(summarized, supplier);
    }

    /** Default compilation of `rename`. */
    public Cog rename(Rename rename) {
        Renaming renaming = rename.getRenaming();
        TupleType tt = rename.getTupleType();

        // stream compilation: simple renaming
        Supplier<Stream<Tuple>> supplier = () -> this.stream()
                .map(t -> t.rename(renaming, tt));

        return new BaseCog(rename, supplier);
    }

    /** Default compilation of `restrict`. */
    public Cog restrict(Restrict restrict) {
        Predicate predicate = restrict.getPredicate();

        // stream compilation: simple filtering
        Supplier<Stream<Tuple>> supplier = () -> this.stream()
                .filter(t -> predicate.test(t));

        return new BaseCog(restrict, supplier);
    }

    /** Default compilation of `join`. */
    public Cog join(Join join, Cog right) {
        AttrList on = join.getJoinAttrList();

        if (on.isEmpty()) {
            return crossJoin(join, right);
        } else {
            return hashJoin(join, right);
        }
    }

    /** Compiles a join with a nested loop */
    private Cog crossJoin(Join join, Cog right) {
        // get some info about the join to apply
        final TupleType tt = join.getTupleType();

        // build a supplier that does the cross join
        Supplier<Stream<Tuple>> supplier = () -> {
            return this.stream().flatMap(leftTuple -> right.stream()
                    .map(rightTuple -> leftTuple.join(rightTuple, tt)));
        };
        return new BaseCog(join, supplier);
    }

    private Cog hashJoin(Join join, Cog right) {
        // get some info about the join to apply
        final AttrList on = join.getJoinAttrList();
        final TupleType tt = join.getTupleType();

        // build a supplier that does the join
        Supplier<Stream<Tuple>> supplier = () -> {
            Stream<Tuple> leftStream = this.stream();
            Stream<Tuple> rightStream = right.stream();

            // build an index on left tuples and do the hash join
            Map<Object, List<Tuple>> leftTuplesIndex = leftStream.collect(groupingBy(t -> t.fetch(on)));
            return rightStream.flatMap(rightTuple -> {
                Object rightKey = rightTuple.fetch(on);
                List<Tuple> leftTuples = leftTuplesIndex.getOrDefault(rightKey, emptyList());
                return leftTuples.stream().map(t -> t.join(rightTuple, tt));
            });
        };
        return new BaseCog(join, supplier);
    }

    /** Default compilation of `union`. */
    public Cog union(Union union, Cog right) {
        // stream compilation: concat + distinct
        Supplier<Stream<Tuple>> supplier = () ->{
            Stream<Tuple> leftStream = this.stream();
            Stream<Tuple> rightStream = right.stream();
            return Stream.of(leftStream,rightStream)
                    .reduce(Stream::concat)
                    .orElse(Stream.empty())
                    .distinct();
        };
        return new BaseCog(union, supplier);
    }
    /** Default compilation of `intersect`. */
    public Cog intersect(Intersect intersect, Cog right) {
        // stream compilation: concat + distinct

        Supplier<Stream<Tuple>> supplier = () ->{
            Stream<Tuple> leftStream = this.stream();
            Stream<Tuple> rightStream = right.stream();
            Set<Tuple> leftHashSet = leftStream.collect(Collectors.toCollection(HashSet::new));
            Stream<Tuple>intersectStream =rightStream
                    .filter(x -> leftHashSet.contains(x));
            return intersectStream ;

        };
        return new BaseCog(intersect, supplier);
    }

    /** Default compilation of `minus`. */
    public Cog minus(Minus minus, Cog right) {
        Supplier<Stream<Tuple>> supplier = () ->{
            Stream<Tuple> leftStream = this.stream();
            Stream<Tuple> rightStream = right.stream();
            Set<Tuple> rightHashSet = rightStream.collect(Collectors.toCollection(HashSet::new));
            Stream<Tuple> minusStream = leftStream
                    .filter(x -> !rightHashSet.contains(x));
            return minusStream;
        };
        return new BaseCog(minus, supplier);
    }

}
