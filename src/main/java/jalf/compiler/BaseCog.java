package jalf.compiler;

import jalf.Relation;
import jalf.Tuple;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class BaseCog extends Cog {

    private Supplier<Stream<Tuple>> streamSupplier;

    public BaseCog(Relation expr, Supplier<Stream<Tuple>> streamSupplier) {
        super(expr);
        this.streamSupplier = streamSupplier;
    }

    public Stream<Tuple> stream() {
        return streamSupplier.get();
    }

}
