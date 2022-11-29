package rldevs4j.base.env.gsmdp.evgen;

import org.apache.commons.math3.random.RandomDataGenerator;
import rldevs4j.base.env.msg.Event;


/**
 *
 * @author Ezequiel Beccaría
 */
// TODO Use the same random number generator in all the test
public class ExponentialExogenousEventGenerator extends ExogenousEventGenerator {
    private final double eventRate; //event exponetial occurence rate
    private final RandomDataGenerator rnd;        

    /**
     * 
     * @param name
     * @param eventRate
     * @param initPhase
     * @param initSigma
     * @param e 
     */
    public ExponentialExogenousEventGenerator(String name, Event e, String initPhase, Double initSigma, double eventRate) {
        super(name, e, initPhase, initSigma);
        this.rnd = new RandomDataGenerator();
        this.eventRate = eventRate;  
    }    

    @Override
    public double nextSigma() {
        double sigma = rnd.nextExponential(eventRate);
        return sigma<0?NEGATIVE_SIGMA_CORRECTOR:sigma;
    }
    
    @Override
    public ExogenousEventGenerator clone(){
        return new ExponentialExogenousEventGenerator(name, getE(), getInitPhase(), getInitSigma(), eventRate);        
    }
    
    @Override
    public void initialize() {
        holdIn(this.getInitPhase(), nextSigma());       
    }
}
