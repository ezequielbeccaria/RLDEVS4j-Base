package rldevs4j.base.agent;

import model.modeling.atomic;
import model.modeling.content;
import model.modeling.message;
import rldevs4j.base.agent.preproc.Preprocessing;
import rldevs4j.base.env.msg.Event;
import rldevs4j.base.env.msg.Step;

/**
 * Agent abstract class (atomic DEVS) that must be implemented by each worker that interacts 
 * with an environment.
 * 
 * Predefined ports:
 *  - step: Inport where the agent receibe enviroment's sensing information.
 *  - action: Outport where the agent send choosed action to environment.
 * @author Ezequiel Beccaría
 * @date 22/10/2018
 */
public abstract class Agent extends atomic {
    private Event nextAction;
    private final Preprocessing preprocessing;
    private final double actionDelay; 
    
    public Agent(String name, Preprocessing preprocessing, double actionDelay) {
        super(name);        
        this.preprocessing = preprocessing;
        this.actionDelay = actionDelay;

        addInport("step");
        addOutport("action");

        initialize();
    }

    @Override
    public void initialize() {
        passivate();
    }

    @Override
    public void deltint() {
        passivate();
    }

    @Override
    public void deltext(double e, message x) {   
        for (int i = 0; i < x.getLength(); i++) {
            if (messageOnPort(x, "step", i)) {
                Step step = ((Step) x.getValOnPort("step", i));    
                if(preprocessing.input(step)){
                    nextAction = observation(preprocessing.getState());
                    setSigma(actionDelay); // activate output function and internal transition function           
                }                
            }
        }
    }

    @Override
    public message out() {
        message m = new message();
        content con = makeContent("action", nextAction);
        m.add(con);
        return m;
    }

    /**
     * Method that compute policy based on Agent implementation.
     * Returns the action selected by the concrete agent.
     *
     * @param step
     * @return
     */
    public abstract Event observation(Step step);
    /**
     * Method that return cumulated reward from the agent in the last episode.
     * @return 
     */
    public abstract double getTotalReward();
    /**
     * Method used to inform to the agent the end of the current episode.
     */
    public void episodeFinished(){
        nextAction = null;
        preprocessing.reset();
        this.clear();
    }   
    public abstract void clear();
    public abstract void saveModel(String path);
}
