package rldevs4j.utils;

import java.util.Collection;

/**
 *
 * @author Ezequiel Beccaría
 */
public class CollectionsUtils {
    
    /**
     * Transform Collection<Double> to double[]
     * @param collection
     * @return 
     */
    public static double[] DoubleToArray(Collection<Double> collection){
        double[] target = new double[collection.size()];
        int i=0;
        for(Double d : collection){
            target[i] = d.doubleValue();
            i++;
        }
        return target;
    }
    
    /**
     * Transform Collection<Integer> to double[]
     * @param collection
     * @return 
     */
    public static int[] IntegerToArray(Collection<Integer> collection){
        int[] target = new int[collection.size()];
        int i=0;
        for(Integer integer : collection){
            target[i] = integer.intValue();
            i++;
        }
        return target;
    }
    
    /**
     * Transform Collection<Long> to double[]
     * @param collection
     * @return 
     */
    public static long[] LongToArray(Collection<Long> collection){
        long[] target = new long[collection.size()];
        int i=0;
        for(Long l : collection){
            target[i] = l.intValue();
            i++;
        }
        return target;
    }
}
