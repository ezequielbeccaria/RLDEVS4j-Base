package rldevs4j.base.agent.preproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import rldevs4j.base.env.msg.Event;
import rldevs4j.base.env.msg.Step;

/**
 *
 * @author Ezequiel Beccaría
 */
public class StackPreprocessing implements Preprocessing{
    private final Queue<Step> steps;
    private final boolean flattenedState;
    private final int stackedObs;
    private final int stackSize;
    private final int stackFactor;
    private final Step initialStep;
    private boolean normalize;
    private INDArray lower;
    private INDArray upper;
    private INDArray range;

    public StackPreprocessing(           
            int stackedObs,
            int stackSize, 
            int stackFactor, 
            boolean flattenedState, 
            Step initialStep,
            boolean normalize, 
            INDArray lower, 
            INDArray upper) {
        this.flattenedState = flattenedState;
        this.stackedObs = stackedObs;
        this.stackSize = stackSize;
        this.stackFactor = stackFactor;
        this.steps = new CircularFifoQueue<>(stackSize);
        this.initialStep = initialStep;
        for(int i=0;i<stackSize;i++)
            this.steps.add(initialStep); // fill the queue with the initial step (initial state)
        if(normalize){
            this.lower = lower;
            this.upper = upper;
            this.range = upper.subRowVector(lower);
        }
        
    }
        
    @Override
    public boolean input(Step step) {
        steps.add(step);        
        return true;       
    }

    @Override
    public Step getState() {        
        List<INDArray> statesToStack = new ArrayList<INDArray>();        
        boolean done = false;
        double reward = 0;
        List<Event> activeActions = null;
        int i=0;        
        for(Step s : steps){
            if(i%stackFactor==0)
                statesToStack.add(s.getObservation());            
            done = s.isDone();
            activeActions = s.getActiveActions();
            reward = s.getReward();
            i++;
        }
        Step obs_step = null;
        while(statesToStack.size()>stackedObs){
            statesToStack.remove(0);
        }
        if(flattenedState){
            obs_step = new Step(Nd4j.toFlattened('c', statesToStack), reward, done, activeActions);                    
        }else{
            obs_step = new Step(Nd4j.vstack(statesToStack), reward, done, activeActions);        
        }    
        if(normalize){
            preprocess(obs_step.getObservation());
        }
        return obs_step;        
    }

    @Override
    public void reset() {                
        steps.clear();        
    }

    @Override
    public Preprocessing clone() {        
        return new StackPreprocessing(stackedObs, stackSize, stackFactor, flattenedState, initialStep, normalize, lower, upper);
    }

    @Override
    public void preprocess(INDArray array) {
        array.subiRowVector(lower.castTo(array.dataType()));
        array.diviRowVector(range.castTo(array.dataType()));
    }
    
}
