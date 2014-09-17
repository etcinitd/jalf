package jalf;

/**
 */
public class TypedAttrName<T> extends AttrName {
    private Class<T> type;

    TypedAttrName(AttrName attrName, Class<T> type) {
        super(attrName.getName());
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public String toString() {
        return "attr(\"" + getName() + "\", " + type.getSimpleName() + ".class)";
    }
}
