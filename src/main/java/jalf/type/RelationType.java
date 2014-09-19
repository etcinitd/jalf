package jalf.type;

import jalf.Relation;
import jalf.Type;

public class RelationType extends HeadingBasedType implements Type<Relation> {

    public RelationType(Heading heading) {
        super(heading);
    }

    @Override
    public Class<Relation> getRepresentationClass() {
        return Relation.class;
    }

}
