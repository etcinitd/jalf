package jalf.constraint;

import static jalf.util.CollectionUtils.setOf;
import jalf.Relation;
import jalf.Renaming;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Keys implements Iterable<Key> {

    private Set<Key> keys;

    public static final Keys EMPTY = new Keys(Key.EMPTY);

    public Keys(Set<Key> keys) {
        this.keys = keys;
    }

    public Keys(Key[] keys) {
        this(setOf(keys));
    }

    public Keys(Key key) {
        this(setOf(key));
    }

    public static Keys keys(Key[] keys) {
        return new Keys(keys);
    }

    @Override
    public Iterator<Key> iterator() {
        return keys.iterator();

    }

    @Override
    public int hashCode() {
        return keys.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Keys other = (Keys) obj;
        return keys.equals(other.keys);
    }

    public Stream<Key> stream() {
        return keys.stream();
    }

    @Override
    public String toString() {
        return "keys("
                + keys.stream()
                .map(k -> k.toString())
                .collect(Collectors.joining(", ")) + ")\n";

    }

    public Keys rename(Renaming renaming) {
        Set<Key> keyrename = this.keys.stream().map(k -> k.rename(renaming))
                .collect(Collectors.toSet());
        return new Keys(keyrename);
    }

    public List<Key> toList() {
        return keys.stream().collect(Collectors.toList());
    }

    public Keys complexUnion(Keys other) {
        Set<Key> keyunion = new HashSet<Key>();
        this.stream().forEach(
                (k) -> {
                    Set<Key> newset = other.keys.stream().map(o -> o.union(k))
                            .collect(Collectors.toSet());
                    keyunion.addAll(newset);
                    ;
                });
        return new Keys(keyunion);
    }

    public Keys simpleUnion(Keys other){
        Set<Key> keyunion = new HashSet<Key>();
        keyunion.addAll(this.keys);
        keyunion.addAll(other.keys);
        return new Keys(keyunion);
    }

    public boolean check(Relation r){
        return this.stream().allMatch(k -> k.check(r)) && this.areKeysValid();
    }

    /**
     * return false if there is a super key of another key
     * @return
     */
    public boolean areKeysValid(){
        Stream<Key> comp = this.stream();
        Set<Key> comp1 = this.keys;
        return comp.anyMatch(k -> {
            Predicate<Key> p = (k1) -> (!k1.equals(k)) && (k1.isSubKey(k) || k1.isSuperKey(k));
            return (comp1.stream().anyMatch(p))? false:true;
        });
    }

}
