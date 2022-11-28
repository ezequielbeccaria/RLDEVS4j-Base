package rldevs4j.base.env;

import java.util.ArrayList;
import java.util.List;
import rldevs4j.base.env.gsmdp.StateObserver;
import rldevs4j.base.env.gsmdp.evgen.ExogenousEventGenerator;
import rldevs4j.base.env.msg.Step;
import rldevs4j.base.env.spaces.DiscreteSpace;
import rldevs4j.base.env.spaces.Space;

/**
 *
 * @author ezequiel
 */
public class BaseEnvironment extends Environment{
    private StateObserver obs;
    private List<ExogenousEventGenerator> evgens;

    public BaseEnvironment(StateObserver obs, List<ExogenousEventGenerator> evgens, String name) {
        super(name);
        this.obs = obs;
        this.evgens = evgens;
        
        add(obs);
        evgens.forEach((ExogenousEventGenerator evgen) -> {
            add(evgen);
            addCoupling(obs, "event_genearator", evgen, "in");
            addCoupling(evgen, "out", obs, "event");
        });
        
        addCoupling(obs, "step", this, "step");
        addCoupling(this, "action", obs, "event");
    }
    

    @Override
    public Environment clone() {
        List<ExogenousEventGenerator> clonedEvgen = new ArrayList<>();
        this.evgens.forEach((ExogenousEventGenerator evgen) -> {            
            clonedEvgen.add(evgen);
        });
        
        return new BaseEnvironment(this.obs.clone(), clonedEvgen, this.name);
    }

    @Override
    public Space getActionSpace() {
        return new DiscreteSpace(2);
    }

    @Override
    public Space getStateSpace() {
        return new DiscreteSpace(3);
    }

    @Override
    public List<Step> getTrace() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
