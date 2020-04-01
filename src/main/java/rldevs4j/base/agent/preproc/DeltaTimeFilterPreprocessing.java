 package rldevs4j.base.agent.preproc;

import java.util.List;
import org.nd4j.linalg.api.ndarray.INDArray;
import rldevs4j.base.env.msg.Event;
import rldevs4j.base.env.msg.Step;


/**
 * Preprocessing class that filter state transitions where de delta time is:
 * last state time - current state time % deltaTime == 0
 * @author Ezequiel Beccaría
 */
public class DeltaTimeFilterPreprocessing implements Preprocessing{
    private final double deltaTime;
    private final double initTime;
    private double lastStateTime;
    private final int timeStatePos;
    private Step step;
    private double cumulativeReward;
    private boolean normalize;
    private INDArray lower;
    private INDArray upper;
    private INDArray range;
     
    public DeltaTimeFilterPreprocessing(double deltaTime, double initTime, int timeStatePos) {
        this(deltaTime, initTime, timeStatePos, false, null, null);
    }

    public DeltaTimeFilterPreprocessing(double deltaTime, double initTime, int timeStatePos, boolean normalize, INDArray lower, INDArray upper) {
        this.deltaTime = deltaTime;
        this.timeStatePos = timeStatePos;
        this.initTime = initTime;
        this.lastStateTime = initTime;
        this.cumulativeReward = 0;          
        this.normalize = normalize;
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
        
        // if preprocessing condition is satisfied AND there are more than default action available
        if(currentTime - lastStateTime >= deltaTime || importantStateChange(step.getActiveActions())){
            this.step = step;
            this.lastStateTime = currentTime;
            return true;
        }else{
            this.cumulativeReward += !step.isDone()?step.getReward():0;
        }    
        return false;
    }

    @Override
    public Step getState() {
        step.addReward(cumulativeReward);
        cumulativeReward = 0;
        if(normalize){
            preprocess(step.getObservation());
        }
        return step;
    }

    @Override
    public void reset() {
        cumulativeReward = 0;
        step = null;
        lastStateTime = initTime;
    }

    @Override
    public Preprocessing clone() {        
        return new DeltaTimeFilterPreprocessing(deltaTime, initTime, timeStatePos, normalize, lower, upper);
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
    public String toString() {
        return "DeltaTimeFilterPreprocessing{" + "deltaTime=" + deltaTime + ", initTime=" + initTime + ", lastStateTime=" + lastStateTime + ", normalize=" + normalize + '}';
    }
    
    @Override
    public void preprocess(INDArray array){        
        array.subiRowVector(lower.castTo(array.dataType()));
        array.diviRowVector(range.castTo(array.dataType()));
    }
}
