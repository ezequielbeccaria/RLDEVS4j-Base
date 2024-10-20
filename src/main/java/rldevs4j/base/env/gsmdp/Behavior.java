package rldevs4j.base.env.gsmdp;

import java.util.List;
import java.util.Map;
import rldevs4j.base.env.gsmdp.evgen.ExogenousEventActivation;
import rldevs4j.base.env.msg.Event;

/**
 * 
 * @author Ezequiel Beccaria
 */
public interface Behavior {
    public void initialize();
    public void trasition(Event e, double time);
    public Map<String, List<Double>> observation();
    public float reward();
    public boolean done();
    public List<Event> enabledActions();
    public ExogenousEventActivation activeEvents();
    public Map<String,Object> info();
    public List<Event> getAllActions();
    public double getSigma();
}
