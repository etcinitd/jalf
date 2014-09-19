package jalf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.unmodifiableMap;

public class Heading {
    private final Map<AttrName, AttrType> attrTypes;
    private final AttrList attrList;

    private Heading(Map<AttrName, AttrType> attrTypes) {
        this.attrTypes = unmodifiableMap(new ConcurrentHashMap<>(attrTypes));
        this.attrList = AttrList.attrs(attrTypes.keySet());
    }

    public static Heading headingOf(Map<AttrName, AttrType> attrTypes) {
        return new Heading(attrTypes);
    }

    public Map<AttrName, AttrType> getAttrTypes() {
        return attrTypes;
    }

    public AttrList getAttrList() {
        return attrList;
    }
}
