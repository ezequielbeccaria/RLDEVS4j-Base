package rldevs4j.base.env.gsmdp.evgen;

import org.apache.commons.math3.random.RandomDataGenerator;
import rldevs4j.base.env.msg.Event;

/**
 *
 * @author Ezequiel Beccaría
 */
public class GaussianExogenousEventGenerator extends ExogenousEventGenerator {
    private final double eventMean; //event occurence mean
    private final double eventStd; //event occurence std
    private final RandomDataGenerator rnd;

    /**
     * 
     * @param name          
     * @param e 
     * @param initPhase
     * @param initSigma
     * @param eventMean
     * @param eventStd 
     */
    public GaussianExogenousEventGenerator(String name, Event e, String initPhase, Double initSigma, double eventMean, double eventStd) {
        super(name, e, initPhase, initSigma);
        this.rnd = new RandomDataGenerator();        
        this.eventMean = eventMean;
        this.eventStd = eventStd;   
    }

    @Override
    public double nextSigma() {        
        double nextSig = rnd.nextGaussian(eventMean, eventStd);
        return nextSig<0?NEGATIVE_SIGMA_CORRECTOR:nextSig;        
    }
    
    @Override
    public ExogenousEventGenerator clone(){
        return new GaussianExogenousEventGenerator(name, getE(), getInitPhase(), getInitSigma(), eventMean, eventStd);        
    }
    }
