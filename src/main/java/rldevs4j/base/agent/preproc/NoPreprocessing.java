package rldevs4j.base.agent.preproc;

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
}
