package rldevs4j.base.env;

import java.util.List;
import model.modeling.digraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import rldevs4j.base.env.msg.Event;
import rldevs4j.base.env.msg.Step;

/**
 * DEVS digraph class that must be implemented to define/model an RL environment.
 * @author Ezequiel Beccaría
 */
public abstract class Environment extends digraph implements Cloneable{
    
    public Environment(String name) {
        super(name);        
                
        addInport("action");
        addOutport("step");
    }
    
    public abstract INDArray getInitialState();
    @Override
    public abstract Environment clone();
    public abstract List<Event> getActionSpace();
    public abstract StateSpaceInfo getStateSpaceInfo();    
    public abstract List<Step> getTrace();
}
