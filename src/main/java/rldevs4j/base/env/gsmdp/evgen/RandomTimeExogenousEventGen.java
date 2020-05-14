package rldevs4j.base.env.gsmdp.evgen;

import model.modeling.content;
import model.modeling.message;
import org.nd4j.linalg.api.rng.Random;
import org.nd4j.linalg.factory.Nd4j;
import rldevs4j.base.env.msg.Event;

import java.util.Map;

/**
 *
 * @author Ezequiel Beccaria
 */
public class RandomTimeExogenousEventGen extends ExogenousEventGenerator{
    private double minTime;
    private double maxTime;
    private int maxEvents;
    private int countEvents;
    private Random rnd;

    public RandomTimeExogenousEventGen(String name, Event e, Double minTime, Double maxTime, int maxEvents) {
        super(name, e, "passive", 0D);
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.maxEvents = maxEvents;
        this.rnd = Nd4j.getRandom();
        this.countEvents = 0;
    }
    
    @Override
    public void initialize() {
        this.countEvents = 0;
        holdIn("passive", 0);
    }

    @Override
    public double nextSigma() {
        double nSigma = INFINITY;
        if(countEvents<maxEvents){
            nSigma = rnd.nextDouble()*(maxTime-minTime)+minTime;
            countEvents++;
        }
        return nSigma;
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e);
    }

    @Override
    public ExogenousEventGenerator clone() {
        return new RandomTimeExogenousEventGen(name, this.getE(), minTime, maxTime, countEvents);
    }
}
