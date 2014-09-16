package jalf;

import static jalf.util.ValidationUtils.validateNotNull;

/**
 * @author amirm
 */
public class AttrName implements Comparable<AttrName> {
    public static AttrName attr(String name) {
        // TODO use a concurrent WeakHashMap to keep immutable AttrName(s)
        return new AttrName(name);
    }

    private String name;

    private AttrName(String name) {
        validateNotNull("Parameter 'name' must be non-null.", name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(AttrName o) {
        return name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AttrName attrName = (AttrName) o;
        return name.equals(attrName.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
