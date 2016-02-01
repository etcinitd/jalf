package jalf.aggregator;

import jalf.AttrName;

public class Count extends Aggregator{
    public Count(AttrName attr, AttrName newnameattr){
        this.nameaggr="count";
        this.attr=attr;
        this.newnameattr= newnameattr;
    }

    public Count(AttrName attr){
        this.nameaggr="count";
        this.attr=attr;
    }

    @Override
    public String getNameAggr() {
        return this.nameaggr;
    }


}
