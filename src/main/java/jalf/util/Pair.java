package jalf.util;

/**
 * Implements a pair of things.
 *
 * @param <T> the type of pair elements.
 */
public class Pair<T> {

    public T left;
    public T right;

    public Pair(T left, T right) {
        this.left = left;
        this.right = right;
    }

}
