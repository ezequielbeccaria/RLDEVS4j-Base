package rldevs4j.base.env.spaces;

/**
 *
 * @author ezequiel
 */
public class BoxSpace implements Space{
    private final float[] minValues;
    private final float[] maxValues;

    public BoxSpace(float[] minValues, float[] maxValues) {
        assert minValues.length == maxValues.length;

        this.minValues = minValues;
        this.maxValues = maxValues;
    }
    

    @Override
    public int size() {
        return minValues.length;
    }

    @Override
    public float[] minValues() {
        return minValues;
    }

    @Override
    public float[] maxValues() {
        return maxValues;
    }
    
}
