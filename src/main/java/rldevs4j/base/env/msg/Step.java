package rldevs4j.base.env.msg;

import GenCol.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
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
@JsonPropertyOrder({ "observation", "reward", "done" })
@JsonIgnoreProperties({ "activeActions" })
public class Step extends entity implements Serializable {
    private INDArray observation;
    private float reward;
    private final boolean done;
    private final List<Event> activeActions;

    public Step(INDArray observation, float reward, boolean done, List<Event> activeActions) {
        this.observation = observation;
        this.reward = reward;
        this.done = done;
        this.activeActions = activeActions;
    }

    public INDArray getObservation() {
        return observation;
    }

    @JsonGetter("observation")
    public double[] getObservationDouble() {
        return observation.toDoubleVector();
    }

    @JsonGetter("reward")
    public double getReward() {
        return reward;
    }
    
    public void addReward(double value){
        reward += value;
    }

    @JsonGetter("done")
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

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<observation.length();i++)
            sb.append(observation.getDouble(i)).append(",");

        sb.append(reward).append(",");
        sb.append(done);
        sb.append("\n");
        return sb.toString();
    }
    
    /**
     * Returns the observation feature in idx position.
     * If idx is negative, return the feature in inverse order.
     * @param idx
     * @return 
     */
    public double getFeature(int idx){
        assert Math.abs(idx) < observation.length();
        if(idx>=0)
            return observation.getDouble(idx);
        else
            return observation.getDouble(observation.length()+idx);
    }
    
    public void setFeature(int idx, double value){
        assert Math.abs(idx) < observation.length();
        if(idx>=0)
            observation.putScalar(new long[]{idx}, value);
        else
            observation.putScalar(new long[]{observation.length()+idx}, value);
    }
    
    public int observationSize(){
        return observation.columns();
    }
    
    public Step clone() {
        return new Step(observation.dup(), reward, done, activeActions);
    }

    public static class IgnoreInheritedIntrospector extends JacksonAnnotationIntrospector {

        public boolean hasIgnoreMarker(final AnnotatedMember m) {
            return m.getDeclaringClass() == entity.class || super.hasIgnoreMarker(m);
        }
    }
}
