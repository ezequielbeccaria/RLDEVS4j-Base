package rldevs4j.base.env.spaces;

/**
 * Defines the observation and action spaces, so you can write generic 
 * code that applies to any Env.
 */
public interface Space {
    public int size();
    public float[] minValues();
    public float[] maxValues();
}
