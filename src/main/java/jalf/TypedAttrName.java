package jalf;

/**
 */
class TypedAttrName<T> extends AttrName {
    private AttrName delegate;
    private Class<T> type;

    TypedAttrName(AttrName attrName, Class<T> type) {
        super(attrName.getName());
        delegate = attrName;
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public TypedAttrName<String> asStr() {
        return delegate.asStr();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public int compareTo(AttrName o) {
        return delegate.compareTo(o);
    }
}
