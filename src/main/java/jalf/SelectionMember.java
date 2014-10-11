package jalf;

import java.util.function.Function;

public class SelectionMember implements Function<Tuple,Object> {

    private Type<?> type;

    private Function<Tuple, ?> impl;

    private SelectionMember(Type<?> type, Function<Tuple,?> impl) {
        this.type = type;
        this.impl = impl;
    }

    public static SelectionMember attrSelection(Type<?> type, AttrName attr) {
        return new AttrSelection(type, attr);
    }

    public static SelectionMember fn(Type<?> type, Function<Tuple,?> impl) {
        return new SelectionMember(type, impl);
    }

    public Type<?> getType() {
        return type;
    }

    @Override
    public Object apply(Tuple t) {
        return impl.apply(t);
    }

    static class AttrSelection extends SelectionMember {

        public AttrSelection(Type<?> type, AttrName attr) {
            super(type, t -> t.get(attr));
        }

    }

}
