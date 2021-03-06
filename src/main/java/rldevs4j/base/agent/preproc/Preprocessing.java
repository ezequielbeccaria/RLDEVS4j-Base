package rldevs4j.base.agent.preproc;

import rldevs4j.base.env.msg.Step;

/**
 * Interface that must be implemented if some custom state preprocessing is 
 * required.
 * @author Ezequiel Beccaría
 * @version 1.0
 */
public interface Preprocessing {
    public boolean input(Step step);
    public Step getState();
    public void reset();
    public Preprocessing clone();
}
