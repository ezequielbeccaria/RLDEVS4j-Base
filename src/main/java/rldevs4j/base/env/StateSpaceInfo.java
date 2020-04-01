package rldevs4j.base.env;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Maintains information about environment's state space
 * @author Ezequiel Beccaría
 */
public class StateSpaceInfo {
    private int stateSpaceDimension;
    private INDArray minValues;
    private INDArray maxValues;
    private String[] labels;

    public StateSpaceInfo(int stateSpaceDimension, INDArray minValues, INDArray maxValues, String[] labels) {
        this.stateSpaceDimension = stateSpaceDimension;
        this.minValues = minValues;
        this.maxValues = maxValues;
        this.labels = labels;
    }

    public int getStateSpaceDimension() {
        return stateSpaceDimension;
    }

    public void setStateSpaceDimension(int stateSpaceDimension) {
        this.stateSpaceDimension = stateSpaceDimension;
    }

    public INDArray getMinValues() {
        return minValues;
    }

    public void setMinValues(INDArray minValues) {
        this.minValues = minValues;
    }

    public INDArray getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(INDArray maxValues) {
        this.maxValues = maxValues;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }
}
