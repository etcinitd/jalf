package jalf.util;

public class TypeUtils {

    public static Class<?> leastCommonSuperclass(Class<?> c1, Class<?> c2) {
      if (c1.isAssignableFrom(c2)) return c1;
      if (c2.isAssignableFrom(c1)) return c2;
      return leastCommonSuperclass(c1.getSuperclass(), c2.getSuperclass());
    }

}
