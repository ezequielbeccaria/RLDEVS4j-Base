package rldevs4j.base.env.gsmdp.evgen;

import org.apache.commons.math3.random.RandomDataGenerator;
import rldevs4j.base.env.msg.Event;


/**
 * Class that generate uniform distribute evetns.
 * TODO: Use the same random number generator in all the test
 * @author Ezequiel Beccaría
 */
public class UniformExogenousEventGenerator extends ExogenousEventGenerator {
    private final double lowerBound; //Uniform distribution lower bound 
    private final double upperBound; //Uniform distribution lower bound
    private final boolean cumulativeUpperBound; 
    private double currentUpper;
    private final RandomDataGenerator rnd;        

    /**
     * 
     * @param name
     * @param e output event intance
     * @param initPhase initial phase of the model
     * @param initSigma initial ta of the model
     * @param lowerBound Uniform distribution lower bound
     * @param upperBound Uniform distribution upper bound (lowerBound <= upperBound)
     * @param cumulativeUpperBound if `true`: U(lowerBound, max(upperBound, t))
     */
    public UniformExogenousEventGenerator (
            String name, 
            Event e, 
            String initPhase, 
            Double initSigma, 
            double lowerBound,
            double upperBound,
            boolean cumulativeUpperBound) throws Exception {        
        super(name, e, initPhase, initSigma);
        
        if (lowerBound > upperBound)
            throw new Exception("`lowerBound` must be <= `upperBound`");
        
        this.rnd = new RandomDataGenerator();
        this.lowerBound = lowerBound;  
        this.upperBound = upperBound;
        this.cumulativeUpperBound = cumulativeUpperBound;
    }    

    @Override
    public double nextSigma() {
        double next_sigma = rnd.nextUniform(lowerBound, Math.max(upperBound, currentUpper));
        if (cumulativeUpperBound)
            currentUpper += next_sigma;
        return next_sigma;
    }
    
    /**
     * Instance clonning method
     * @return
     */
    @Override
    public ExogenousEventGenerator clone(){
        try {
            return new UniformExogenousEventGenerator(
                    name, 
                    getE(), 
                    getInitPhase(), 
                    getInitSigma(), 
                    lowerBound, 
                    upperBound,
                    cumulativeUpperBound
                );        
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public void initialize() {
        currentUpper = 0;
        holdIn(this.getInitPhase(), nextSigma());       
    }
}
