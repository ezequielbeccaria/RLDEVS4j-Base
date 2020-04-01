package rldevs4j.base.agent.preproc;

import org.nd4j.linalg.api.ndarray.INDArray;
import rldevs4j.base.env.msg.Step;

/**
 * No preprocessing class.
 * @author Ezequiel Beccaría
 */
public class NoPreprocessing implements Preprocessing{
    private Step currentStep;

    @Override
    public boolean input(Step step) {
        currentStep = step;
        return true;
    }

    @Override
    public Step getState() {
        return currentStep;
    }

    @Override
    public void reset() {
        currentStep = null;
    }

    @Override
    public Preprocessing clone() {
        return new NoPreprocessing();
    }

    @Override
    public void preprocess(INDArray state) {
        
    }
    
}
