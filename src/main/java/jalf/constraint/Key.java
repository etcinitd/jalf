package jalf.constraint;

import java.util.stream.Collectors;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;

public class Key  implements Constraint{

    public static final Key EMPTY = new Key(AttrList.EMPTY);

    private AttrList attrsKey;

    public Key(AttrList attrsKey) {
        super();
        this.attrsKey=attrsKey;
    }

    public  static Key candidate(AttrList attrsKey) {
        return new Key(attrsKey);
    }

    public AttrList toAttrList() {
        return attrsKey;
    }

    public Key rename(Renaming renaming) {
        AttrList newkey= this.attrsKey.rename(renaming);
        return new Key(newkey);
    }

    public Key union(Key other) {
        AttrList newkey=  this.attrsKey.union(other.attrsKey);
        return new Key(newkey);
    }

    public boolean isSuperKey(Key otherkey){
        return this.attrsKey.intersect(otherkey.toAttrList()).equals(otherkey.toAttrList());
    }

    public boolean isSubKey(Key otherkey){
        return this.attrsKey.intersect(otherkey.toAttrList()).equals(this.attrsKey);
    }

    public boolean isSubSetOf(AttrList attributes) {
        return this.attrsKey.intersect(attributes).equals(this.attrsKey);
    }

    @Override
    public int hashCode() {
        return attrsKey.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof Key))
            return false;
        Key otherkey = (Key) o;
        return (this.attrsKey.equals(otherkey.attrsKey));
    }

    @Override
    public boolean check(Relation r) {
        //vérifier si les clef appartienne bien au atributs de la relation
        AttrList attrsrelation= r.getType().getHeading().toAttrList();
        AttrList intersect= attrsrelation.intersect(this.attrsKey);
        if (intersect.equals(this.attrsKey)){
            // deuxieme vérification sur la cardinality apres projection
            // sur la clef
            return (r.project(this.attrsKey).cardinality() == r.cardinality());
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        String s = "key( ";
        s += this.attrsKey.stream()
                .map(a -> a.getName())
                .collect(Collectors.joining(", "));
        s += ")";
        return s;
    }

}
