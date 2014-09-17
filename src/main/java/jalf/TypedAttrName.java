package jalf;

/**
 */
public class TypedAttrName<T> extends AttrName {
    private AttrName delegate;
    private Class<T> type;

    TypedAttrName(AttrName attrName, Class<T> type) {
        super(attrName.getName());
        // always use the root delegate
        if (attrName instanceof TypedAttrName)
            delegate = ((TypedAttrName) attrName).delegate;
        else
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
    public String getName() {
        return delegate.getName();
    }

    @Override
    public int compareTo(AttrName o) {
        return delegate.compareTo(o);
    }

    @Override
    public <U> TypedAttrName<U> as(Class<U> type) {
        return new TypedAttrName<>(delegate, type);
    }

    @Override
    public String toString() {
        return "attr(\"" + getName() + "\", " + type.getSimpleName() + ".class)";
    }
}
