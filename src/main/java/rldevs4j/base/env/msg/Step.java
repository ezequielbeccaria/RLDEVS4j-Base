package rldevs4j.base.env.msg;

import GenCol.entity;
import java.util.List;
import org.nd4j.linalg.api.ndarray.INDArray;

/**     
 * Environment's return to the agent based on OpenAI's Gym Step.
 * observation: An environment-specific object representing your observation of the environment.
 * reward: Amount of reward achieved by the previous action.
 * done: Determine when it’s time to reset the environment again. 
 * Most (but not all) tasks are divided up into well-defined episodes, and "done"
 * being "true" indicates the episode has terminated. * 
 * @author Ezequiel Beccaría 
 */
public class Step extends entity {
    private INDArray observation;
    private double reward;
    private boolean done;
    private List<Event> activeActions;

    public Step(INDArray observation, double reward, boolean done, List<Event> activeActions) {
        this.observation = observation;
        this.reward = reward;
        this.done = done;
        this.activeActions = activeActions;
    }

    public INDArray getObservation() {
        return observation;
    }

    public double getReward() {
        return reward;
    }
    
    public void addReward(double value){
        reward += value;
    }

    public boolean isDone() {
        return done;
    }

    public List<Event> getActiveActions() {
        return activeActions;
    }

    public void setObservation(INDArray observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "Step{" + "observation=" + observation + ", reward=" + reward + ", done=" + done + '}';
    }
    
    
}
