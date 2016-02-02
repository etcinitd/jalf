package jalf.aggregator;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Tuple;

/**
 * This class represent the expression that it take by an aggregator such that max, min, etc
 * The expression is a list of field applied to an operator
 * Exemple : Max(QTY)
 * @author aimable
 *
 */
public class Expression{

    private AttrName aggregatedField;
    private String operator = "";

    public Expression(AttrName aggregatedField){
        this.aggregatedField = aggregatedField;
    }

    public AttrName getField(){
        return this.aggregatedField;
    }

    public static Expression getInstance(AttrName aggregatedField){
        return new Expression(aggregatedField);
    }

    /**
     * compute a max aggregation
     *
     * @param t1
     * @param t2
     * @return
     */
    public int maxmin(Tuple t1, Tuple t2){
        Object one = t1.fetch(AttrList.attrs(this.aggregatedField)).get(0);
        Object two = t2.fetch(AttrList.attrs(this.aggregatedField)).get(0);

        System.out.println(one);
        System.out.println(two);
        if (one.getClass().equals(Integer.class)){
            return ((Integer)one).compareTo((Integer)two);
        }
        else if(one.getClass().equals(Double.class)){
            return ((Double)one).compareTo((Double)two);
        }
        else if(one.getClass().equals(String.class)){
            return ((String)one).compareTo((String)two);
        }

        return 0;
    }

}
