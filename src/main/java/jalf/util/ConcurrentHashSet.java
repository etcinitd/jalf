package jalf.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ConcurrentHashSet<E>
        implements Set<E>, Serializable {
    private final ConcurrentHashMap<E, Boolean> map;
    private transient Set<E> keySet;

    public ConcurrentHashSet() {
        map = new ConcurrentHashMap<>();
        keySet = map.keySet();
    }

    public void clear() {
        map.clear();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    public boolean add(E e) {
        return map.put(e, Boolean.TRUE) == null;
    }

    public Iterator<E> iterator() {
        return keySet.iterator();
    }

    public Object[] toArray() {
        return keySet.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return keySet.toArray(a);
    }

    public String toString() {
        return keySet.toString();
    }

    public int hashCode() {
        return keySet.hashCode();
    }

    public boolean equals(Object o) {
        return o == this || keySet.equals(o);
    }

    public boolean containsAll(Collection<?> c) {
        return keySet.containsAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return keySet.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return keySet.retainAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        HashMap<E, Boolean> mapped = new HashMap<>();
        c.forEach(e -> mapped.put(e, Boolean.TRUE));
        map.putAll(mapped);
        return true;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        keySet.forEach(action);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return keySet.removeIf(filter);
    }

    @Override
    public Spliterator<E> spliterator() {
        return keySet.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return keySet.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return keySet.parallelStream();
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        keySet = map.keySet();
    }
}
