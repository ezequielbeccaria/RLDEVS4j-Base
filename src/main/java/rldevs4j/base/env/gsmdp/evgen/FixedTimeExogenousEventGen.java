package rldevs4j.base.env.gsmdp.evgen;

import rldevs4j.base.env.msg.Event;

/**
 *
 * @author Ezequiel Beccaria
 */
public class FixedTimeExogenousEventGen extends ExogenousEventGenerator{
    private final Double[] eventTimes;
    private int currentIdx;

    public FixedTimeExogenousEventGen(String name, Event e, Double[] eventTimes) {
        super(name, e, "passive", 0D);
        this.eventTimes = eventTimes;
        currentIdx = 0;
    }
    
    @Override
    public void initialize() {        
        holdIn("passive", 0);   
        currentIdx = 0;
    }

    @Override
    public double nextSigma() {
        double nSigma = INFINITY;
        if(currentIdx<eventTimes.length){
            nSigma = eventTimes[currentIdx];
            currentIdx++;
        }
        return nSigma;
    }

    @Override
    public ExogenousEventGenerator clone() {
        return new FixedTimeExogenousEventGen(name, this.getE(), eventTimes);
    }
    
}
