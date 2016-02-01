package jalf.aggregator;

import jalf.AttrName;

public class Max extends Aggregator{
    public Max(AttrName attr, AttrName newnameattr){
        this.nameaggr="max";
        this.attr=attr;
        this.newnameattr=newnameattr;

    }
    public Max(AttrName attr){
        this.nameaggr="max";
        this.attr=attr;
    }
}
