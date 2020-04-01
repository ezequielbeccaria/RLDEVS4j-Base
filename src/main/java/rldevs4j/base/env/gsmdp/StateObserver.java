package rldevs4j.base.env.gsmdp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.modeling.atomic;
import model.modeling.content;
import model.modeling.message;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.string.NDArrayStrings;
import rldevs4j.base.env.msg.Event;
import rldevs4j.base.env.msg.Step;

/*     
 * Component responsible for maintaining the state of the environment, 
 * including a global environment clock value, and update it based on the 
 * arrival of exogenous events $e_i$ or actions $a_i$ executed by the agent.
 * @author Ezequiel Beccaría 
 */
public class StateObserver extends atomic implements Cloneable{

    protected INDArray state;
    protected INDArray prevState;
    protected float reward;
    protected Behavior behavior;
    protected boolean debug;
    protected NDArrayStrings toString;
    protected List<Event> lastTransEvents;

    public StateObserver(
            Behavior behavior,
            boolean debug) {
        super("State Observer");
        this.reward = 0;
        this.behavior = behavior;
        this.debug = debug;
        this.prevState = null;
        this.lastTransEvents = new ArrayList<>();
        this.state = behavior.observation();

        addInport("event");
        addOutport("event_genearator");
        addOutport("step");
    }

    @Override
    public void initialize() {   
        prevState = null;
        passivate();
    }

    @Override
    public void deltint() {
        prevState = null;
        lastTransEvents.clear();
        setSigma(INFINITY);
    }

    @Override
    public void deltext(double e, message x) {
        //set state time
        updateStateDate(state, e);
        //copy of previus state
        prevState = state.detach();
        //Iterate over messages and update state
        for (int i = 0; i < x.getLength(); i++) {
            if (messageOnPort(x, "event", i)) {
                Event event = (Event) x.getValOnPort("event", i);
                lastTransEvents.add(event);
                behavior.trasition(state.detach(), event);
                state = behavior.observation();
                reward = behavior.reward();                
                setSigma(0);
            }
        }                    
    }

    @Override
    public message out() {
        boolean done = behavior.done();
        message m = new message();
        if(this.sigma != INFINITY){
            //activate/deactiva exogenous events
            content con_event_gen = makeContent(
                    "event_genearator",
                    behavior.activeEvents());
            m.add(con_event_gen);

            //sent new state, actios and reward to agent
            content con_agent = makeContent(
                "step",
                new Step(
                    state.dup(), 
                    reward, 
                    done, 
                    behavior.enabledActions()));
            m.add(con_agent);
        }
        return m;
    }

    protected String debugTransition(INDArray prevState, Event event, INDArray state, double reward) {
        return "prevState=" + toString.format(prevState) + ", event=" + event.toString() + ", reward=" + String.format(Locale.US, "%07.2f", reward) + "}\n";
    }

    public void setState(INDArray state) {
        this.state = state;
        this.prevState = null;
    }
    
    protected void updateStateDate(INDArray state, Double e){
        state.putScalar(state.columns()-1, state.getDouble(state.columns()-1)+e);
    }
    
    @Override
    public StateObserver clone(){
        return new StateObserver(behavior, debug);
    }
}    
