
package rldevs4j.base.agent;

import rldevs4j.base.agent.preproc.Preprocessing;
import rldevs4j.base.env.msg.Event;
import rldevs4j.base.env.msg.Step;


/**
 *
 * @author Ezequiel Beccaria
 */
public class DummyAgent extends Agent{
    private double cumReward = 0D;
    private final Event defaultAction;


    public DummyAgent(String name, Preprocessing preprocessing, Event defaultAction) {
        super(name, preprocessing, 1D);
        this.defaultAction = defaultAction;
    }

    @Override
    public Event observation(Step step) {
        cumReward += step.getReward();       
        return defaultAction;
    }

    @Override
    public double getTotalReward() {
        return cumReward;
    }

    @Override
    public void trainingFinished() {

    }

    @Override
    public void clear() {
        cumReward = 0D;
    }

    @Override
    public void saveModel(String path) {        
    }

    @Override
    public void loadModel(String path) {

    }

}
