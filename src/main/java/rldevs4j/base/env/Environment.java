package rldevs4j.base.env;

import java.util.List;
import model.modeling.digraph;
import rldevs4j.base.env.msg.Step;
import rldevs4j.base.env.spaces.Space;

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
    
    @Override
    public abstract Environment clone();
    public abstract Space getActionSpace();
    public abstract Space getStateSpace();
    public abstract List<Step> getTrace();
}
