package jalf.relation.csv;

import static jalf.util.CollectionUtils.mapOf;
import jalf.AttrName;
import jalf.Relation;
import jalf.Tuple;
import jalf.Type;
import jalf.Visitor;
import jalf.compiler.AbstractRelation;
import jalf.compiler.BaseCog;
import jalf.compiler.Cog;
import jalf.compiler.Compiler;
import jalf.constraint.Keys;
import jalf.relation.algebra.LeafOperand;
import jalf.relation.materialized.SetMemoryRelation;
import jalf.type.Heading;
import jalf.type.RelationType;
import jalf.type.TupleType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import au.com.bytecode.opencsv.CSVReader;

public class CsvRelation extends AbstractRelation implements LeafOperand {

    private Supplier<InputStream> csvStreamSupplier;

    private RelationType type;

    private AttrName[] attrNames;

    public CsvRelation(Supplier<InputStream> supplier) throws IOException {
        csvStreamSupplier = supplier;
        installType();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Cog toCog(Compiler compiler) {
        return new BaseCog(this, () -> getCsvTupleStream());
    }

    @Override
    public boolean equals(Relation other) {
        Relation r = this.stream().collect(SetMemoryRelation.collector(other.getType()));
        return r.equals(other);
    }

    ///

    private void installType() throws IOException {
        try (CSVReader reader = getCsvReader()) {
            Type<?> stringType = Type.scalarType(String.class);
            attrNames = AttrName.attrs(reader.readNext());
            Heading h = Heading.dress(attrNames, (name) -> stringType);
            type = RelationType.dress(h);
        }
    }

    private Stream<Tuple> getCsvTupleStream() {
        TupleType tupleType = getType().toTupleType();
        return StreamSupport
                .stream(new CsvSpliterator(() -> getCsvReader()), false)
                .map((attrValues) -> {
                    return new Tuple(tupleType, mapOf(attrNames, attrValues));
                });
    }

    private CSVReader getCsvReader() {
        return new CSVReader(new InputStreamReader(csvStreamSupplier.get()), ';');
    }

    static class CsvSpliterator implements Spliterator<String[]> {

        private Supplier<CSVReader> readerSupplier;

        private CSVReader reader;

        private boolean closed = false;

        public CsvSpliterator(Supplier<CSVReader> supplier) {
            readerSupplier = supplier;
        }

        @Override
        public boolean tryAdvance(Consumer<? super String[]> action) {
            if (closed) { return false; }
            try {
                String[] line = getReader().readNext();
                if (line != null) {
                    action.accept(line);
                    return true;
                } else {
                    reader.close();
                    closed = true;
                    return false;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public Spliterator<String[]> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return DISTINCT | NONNULL | IMMUTABLE;
        }

        private CSVReader getReader() throws IOException {
            if (reader == null) {
                reader = readerSupplier.get();
                reader.readNext();
                closed = false;
            }
            return reader;
        }
    }

    @Override
    public Keys getKeys() {
        return new Keys(type.getLargestKey());
    }

}
