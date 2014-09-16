package jalf;

import java.util.Map;
import java.util.function.UnaryOperator;

import static jalf.util.ValidationUtils.validateNotNull;
import static java.util.Collections.unmodifiableMap;

/**
 * Captures the renaming of some attribute names.
 *
 * This class is not intended to be used by end-users, please use Jalf's DSL
 * instead.
 */
public class Renaming implements UnaryOperator<AttrName> {

    private Map<AttrName, AttrName> renamings;

    public Renaming(Map<AttrName, AttrName> renamings) {
        validateNotNull("Parameter 'renamings' must be non-null.", renamings);
        this.renamings = unmodifiableMap(renamings);
    }

    @Override
    public AttrName apply(AttrName t) {
        return renamings.get(t);
    }

    @Override
    public int hashCode() {
        return renamings.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Renaming other = (Renaming) obj;
        return renamings.equals(other.renamings);
    }

}
