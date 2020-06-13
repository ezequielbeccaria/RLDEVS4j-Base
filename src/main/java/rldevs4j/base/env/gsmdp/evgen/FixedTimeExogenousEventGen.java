package rldevs4j.base.env.gsmdp.evgen;

import rldevs4j.base.env.msg.Event;

/**
 * Fixed interval event generator
 * If repeat==true, currentIdx become zero when reach max eventTimes length.
 * @author Ezequiel Beccaria
 */
public class FixedTimeExogenousEventGen extends ExogenousEventGenerator{
    private final Double[] eventTimes;
    private int currentIdx;
    private final boolean repeat;

    public FixedTimeExogenousEventGen(String name, Event e, Double[] eventTimes) {
        this(name, e, eventTimes, false);
    }

    public FixedTimeExogenousEventGen(String name, Event e, Double[] eventTimes, boolean repeat) {
        super(name, e, "passive", 0D);
        this.eventTimes = eventTimes;
        this.repeat = repeat;
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
            if(repeat && currentIdx == eventTimes.length)
                currentIdx = 0;
        }
        return nSigma;
    }

    @Override
    public ExogenousEventGenerator clone() {
        return new FixedTimeExogenousEventGen(name, this.getE(), eventTimes, repeat);
    }
    
}
