
package rldevs4j.utils;

/**
 *
 * @author Ezequiel Beccaria
 */
public class ArrayMath {

    public static int[] sum(int[] arrOne, int[] arrTwo) {
        if(arrOne.length!=arrTwo.length) {// check if length is same
            return null;
        }
        int[] returnArray = new int[arrOne.length];
        for(int i=0;i<arrOne.length;i++) { // adding with same index
            returnArray[i]=arrOne[i]+arrTwo[i];
        }
        return returnArray;
    }

    public static int[] subtract(int[] arrOne, int[]  arrTwo) {
        if(arrOne.length!=arrTwo.length) { // checking length is same
            return null;
        }
        int[] returnArray = new int[arrOne.length];
        for(int i=0;i<arrOne.length;i++) { // subtracting with same index
            returnArray[i]=arrOne[i]-arrTwo[i];
        }
        return returnArray;
    }
    
    public static long[] sum(long[] arrOne, long[] arrTwo) {
        if(arrOne.length!=arrTwo.length) {// check if length is same
            return null;
        }
        long[] returnArray = new long[arrOne.length];
        for(int i=0;i<arrOne.length;i++) { // adding with same index
            returnArray[i]=arrOne[i]+arrTwo[i];
        }
        return returnArray;
    }

    public static long[] subtract(long[] arrOne, long[]  arrTwo) {
        if(arrOne.length!=arrTwo.length) { // checking length is same
            return null;
        }
        long[] returnArray = new long[arrOne.length];
        for(int i=0;i<arrOne.length;i++) { // subtracting with same index
            returnArray[i]=arrOne[i]-arrTwo[i];
        }
        return returnArray;
    }
    
    public static double[] sum(double[] arrOne, double[] arrTwo) {
        if(arrOne.length!=arrTwo.length) {// check if length is same
            return null;
        }
        double[] returnArray = new double[arrOne.length];
        for(int i=0;i<arrOne.length;i++) { // adding with same index
            returnArray[i]=arrOne[i]+arrTwo[i];
        }
        return returnArray;
    }

    public static double[] subtract(double[] arrOne, double[] arrTwo) {
        if(arrOne.length!=arrTwo.length) { // checking length is same
            return null;
        }
        double[] returnArray = new double[arrOne.length];
        for(int i=0;i<arrOne.length;i++) { // subtracting with same index
            returnArray[i]=arrOne[i]-arrTwo[i];
        }
        return returnArray;
    }

}
