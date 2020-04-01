package rldevs4j.utils;

/**
 *
 * @author ezequiel
 */
public class Nd4jUtils {
    public static int[] makeShape(int size, long[] shape) {
        int[] nshape = new int[shape.length + 1];
        nshape[0] = size;
        System.arraycopy(shape, 0, nshape, 1, shape.length);
        return nshape;
    }

    public static int[] makeShape(int batch, long[] shape, int length) {
        int[] nshape = new int[3];
        nshape[0] = batch;
        nshape[1] = 1;
        for (int i = 0; i < shape.length; i++) {
            nshape[1] *= shape[i];
        }
        nshape[2] = length;
        return nshape;
    }
}
