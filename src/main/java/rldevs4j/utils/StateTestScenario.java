package rldevs4j.utils;

import java.util.List;
import org.nd4j.linalg.api.ndarray.INDArray;
import rldevs4j.base.env.msg.Event;

/**
 * Class used to test state-action choosing scenario
 * @author Ezequiel Beccaría
 */
public class StateTestScenario {
    private final INDArray state;
    private final List<Event> activeActions;

    public StateTestScenario(INDArray state, List<Event> activeActions) {
        this.state = state;
        this.activeActions = activeActions;
    }

    public INDArray getState() {
        return state;
    }

    public List<Event> getActiveActions() {
        return activeActions;
    }
}
