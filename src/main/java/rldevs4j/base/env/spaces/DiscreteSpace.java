package rldevs4j.base.env.spaces;

/**
 *
 * @author ezequiel
 */
public class DiscreteSpace implements Space{
    private final int size;
    
    public DiscreteSpace(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public float[] minValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float[] maxValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
