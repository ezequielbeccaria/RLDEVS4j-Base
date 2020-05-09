package rldevs4j.base.env.gsmdp;

import java.util.List;
import org.nd4j.linalg.api.ndarray.INDArray;
import rldevs4j.base.env.gsmdp.evgen.ExogenousEventActivation;
import rldevs4j.base.env.msg.Event;

/**
 * 
 * @author Ezequiel Beccaria
 */
public interface Behavior {
    public void initialize();
    public void trasition(Event e, double time);
    public INDArray observation();
    public float reward();
    public boolean done();
    public List<Event> enabledActions();
    public ExogenousEventActivation activeEvents();
    public List<Event> getAllActios();
    public boolean notifyAgent();
}
