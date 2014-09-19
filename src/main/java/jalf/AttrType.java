package jalf;

public class AttrType {

    public static AttrType NULL_TYPE = new AttrType();
    private Class<?> javaClass;

    private AttrType() {
    }

    public AttrType(Class<?> javaClass) {
        this.javaClass = javaClass;
    }

    public static AttrType typeOf(Object val) {
        if (val == null) {
            return NULL_TYPE;
        }
        return new AttrType(val.getClass());
    }
}
