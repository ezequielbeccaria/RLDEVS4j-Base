package rldevs4j.base.env.msg;

/**     
 * Multi-Categorical Event with parametric values of type T.
 * 
 * @author Ezequiel Beccaría
 */
public class GaussianContinuous extends Event { 
    private final double[] mean;
    private final double[] std;
    
    public GaussianContinuous(int id, String name, EventType type, double[] mean, double[] std) {
        super(id, name, type);
        assert mean.length == std.length : "mean and std must have the same number of elements.";
        this.mean = mean;
        this.std = std;
    }

    public int size(){
        return mean.length;
    }

    public double[] getMean() {
        return mean;
    }

    public double[] getStd() {
        return std;
    }    
}
