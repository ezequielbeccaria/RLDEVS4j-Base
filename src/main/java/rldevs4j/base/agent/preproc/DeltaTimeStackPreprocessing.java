package rldevs4j.base.agent.preproc;

import java.util.ArrayList;
import java.util.Collections;
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
public class DeltaTimeStackPreprocessing implements Preprocessing{
    private final double deltaTime;
    private final double initTime;
    private double lastStateTime;
    private final int timeStatePos;
    private final Queue<Step> steps;
    private double cumulativeReward;
    private final boolean flattenedState;
    private final int stackedObs;
    private final int stackSize;
    private final int stackFactor;
    private final Step initialStep;
    private boolean normalize;
    private INDArray lower;
    private INDArray upper;
    private INDArray range;
    
    public DeltaTimeStackPreprocessing(
            double deltaTime, 
            double initTime, 
            int timeStatePos, 
            int stackedObs,
            int stackSize, 
            int stackFactor, 
            boolean flattenedState, 
            Step initialStep){
        this(deltaTime, initTime, timeStatePos, stackedObs, stackSize, 
                stackFactor, flattenedState, initialStep, false, null, null);
    }

    public DeltaTimeStackPreprocessing(
            double deltaTime, 
            double initTime, 
            int timeStatePos, 
            int stackedObs,
            int stackSize, 
            int stackFactor, 
            boolean flattenedState, 
            Step initialStep, 
            boolean normalize, 
            INDArray lower, 
            INDArray upper) {
        this.deltaTime = deltaTime;
        this.initTime = initTime;
        this.lastStateTime = initTime;
        this.timeStatePos = timeStatePos;        
        this.cumulativeReward = 0;
        this.flattenedState = flattenedState;
        this.stackedObs = stackedObs;
        this.stackSize = stackSize;
        this.stackFactor = stackFactor;
        this.steps = new CircularFifoQueue<Step>(stackSize);
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
        INDArray state = step.getObservation();
        double currentTime = state.getDouble(timeStatePos);
        steps.add(step);
        // if preprocessing condition is satisfied AND there are more than default action available
        if(currentTime - lastStateTime >= deltaTime || importantStateChange(step.getActiveActions())){
            this.lastStateTime = currentTime;
            return true;
        }else{
            this.cumulativeReward += !step.isDone()?step.getReward():0;
        }    
        return false;
    }

    @Override
    public Step getState() {        
        List<INDArray> statesToStack = new ArrayList<INDArray>();        
        boolean done = false;
        List<Event> activeActions = null;
        int i=0;
        List<Step> reversed = new ArrayList<>(steps);
        Collections.reverse(reversed);
        for(Step s : reversed){
            if(i%stackFactor==0){
                statesToStack.add(s.getObservation());            
                if(i==0){
                    done = s.isDone();
                    activeActions = s.getActiveActions();
                }
            }
            i++;
            if(statesToStack.size()==stackedObs)
                break;
        }
        Step obs_step = null;        
        if(flattenedState){
            obs_step = new Step(Nd4j.toFlattened('c', statesToStack), cumulativeReward, done, activeActions);                
        }else{
            obs_step = new Step(Nd4j.vstack(statesToStack), cumulativeReward, done, activeActions);        
        }    
        cumulativeReward = 0;
        if(normalize){
            preprocess(obs_step.getObservation());
        }
        return obs_step;        
    }

    @Override
    public void reset() {        
        cumulativeReward = 0;
        steps.clear();
        lastStateTime = initTime;
        for(int i=0;i<stackSize;i++)
            this.steps.add(initialStep); // fill the queue with the initial step (initial state)
    }

    @Override
    public Preprocessing clone() {
        return new DeltaTimeStackPreprocessing(
                deltaTime, initTime, timeStatePos, stackedObs, stackSize, 
                stackFactor, flattenedState, initialStep, normalize, lower, upper);
    }
    
    private boolean importantStateChange(List<Event> activeActions){
        boolean importantChange = false;
        for(Event e : activeActions){
            if(e.getId() == 2)
                importantChange = true;
        }
        return importantChange;
    }

    @Override
    public void preprocess(INDArray array) {
        array.subiRowVector(lower.castTo(array.dataType()));
        array.diviRowVector(range.castTo(array.dataType()));
    }
    
}
