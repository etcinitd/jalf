package jalf;

/**
 * @author amirm
 */
public class AttrValue {
    private Object value;

    // TODO what to do about null?
    public AttrValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttrValue attrValue = (AttrValue) o;

        if (value != null ? !value.equals(attrValue.value) : attrValue.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
