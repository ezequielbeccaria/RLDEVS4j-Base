package rldevs4j.base.env.gsmdp.evgen;

import rldevs4j.base.env.msg.Event;

/**
 *
 * @author Ezequiel Beccaría
 */
public class GaussianDecayEventGenerator extends ExogenousEventGenerator {
    private final double initMean;    
    private final double meanDecay;
    private final double minMean;
    private final double maxMean;
    private final double factor; 
    private final double initStd; //event occurence std
    private int count = 0;

    /**
     * 
     * @param name
     * @param e
     * @param initPhase
     * @param initMean
     * @param initStd
     * @param meanDecay
     * @param minMean
     * @param maxMean 
     * @param factor 
     */
    public GaussianDecayEventGenerator(String name, Event e, String initPhase, 
            double initMean, double initStd, double meanDecay,
            double minMean, double maxMean, double factor) {
        super(name, e, initPhase, initMean);      
        this.initMean = initMean;
        this.initStd = initStd;   
        this.meanDecay = meanDecay;
        this.minMean = minMean;
        this.maxMean = maxMean;
        this.factor = factor;        
    }

    @Override
    public ExogenousEventGenerator clone(){
        return new GaussianDecayEventGenerator(name, getE(), getInitPhase(), 
                getInitSigma(), initStd, meanDecay, minMean, maxMean, factor);        
    }
    
    @Override
    public double nextSigma(){
        double nextSigma; 
        if(factor == 1){
            nextSigma = Math.min(maxMean, initMean * Math.exp(meanDecay*count));            
        }else{
            nextSigma = Math.max(minMean, initMean * Math.exp(-meanDecay*count)); 
        }    
        count++;
        return nextSigma;
    }
    
    @Override
    public void initialize() {
        super.initialize();
        count = 0;
    }
}
