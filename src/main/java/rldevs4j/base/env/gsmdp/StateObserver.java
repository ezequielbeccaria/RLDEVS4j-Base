package rldevs4j.base.env.gsmdp;

import java.util.ArrayList;
import java.util.List;

import model.modeling.atomic;
import model.modeling.content;
import model.modeling.message;
import model.simulation.CoordinatorInterface;
import model.simulation.CoupledCoordinatorInterface;
import rldevs4j.base.env.gsmdp.evgen.ExogenousEventActivation;
import rldevs4j.base.env.msg.Event;
import rldevs4j.base.env.msg.Step;

/*     
 * Component responsible for maintaining the state of the environment, 
 * including a global environment clock value, and update it based on the 
 * arrival of exogenous events $e_i$ or actions $a_i$ executed by the agent.
 * @author Ezequiel Beccaría 
 */
public class StateObserver extends atomic implements Cloneable{
    protected float reward;
    protected Behavior behavior;
    protected boolean debug;
    protected List<Step> trace;

    public StateObserver(
            Behavior behavior,
            boolean debug) {
        super("State Observer");
        this.behavior = behavior;
        this.debug = debug;
        this.trace = new ArrayList<>();
        this.reward = 0F;

        addInport("event");
        addOutport("event_genearator");
        addOutport("step");
    }

    @Override
    public void initialize() {  
        trace.clear();
        behavior.initialize();
        holdIn("passive", 0);
    }

    @Override
    public void deltint() {
        behavior.trasition(null, currentGlobalTime());        
        trace.add(
            new Step(
                behavior.observation(),
                reward,
                behavior.done(),
                behavior.enabledActions()
            )
        );
        reward = 0F;
        holdIn("passive", behavior.getSigma());
    }

    @Override
    public void deltext(double e, message x) {
        //Iterate over messages and update state
        Event event = null;
        for (int i = 0; i < x.getLength(); i++) {
            if (messageOnPort(x, "event", i)) {
                behavior.trasition(
                        (Event) x.getValOnPort("event", i),
                        currentGlobalTime()
                );
            }
        }            
        reward += behavior.reward(); // reward acumulation for multiple events
        setSigma(behavior.getSigma() - e);
    }

    @Override
    public message out() {
        message m = new message();

        //activate/deactiva exogenous events
        ExogenousEventActivation eea = behavior.activeEvents();
        if(eea != null){
            content con_event_gen = makeContent(
                    "event_genearator",
                    behavior.activeEvents());
            m.add(con_event_gen);
        }
        //sent new state, actios and reward to agent
        content con_agent = makeContent("step", trace.get(trace.size()-1).clone());
        m.add(con_agent);
        return m;
    }

    @Override
    public StateObserver clone(){
        return new StateObserver(behavior, debug);
    }

    public List<Step> getTrace() {
        return trace;
    }

    private Double currentGlobalTime(){
        CoordinatorInterface globalCoordnator = this.getSim().getRootParent();
        CoupledCoordinatorInterface parent = this.getSim().getParent();
        while(globalCoordnator==null){
            globalCoordnator = parent.getRootParent();
            parent = parent.getParent();
        }        
        return globalCoordnator.getTL();
    }  
}    
