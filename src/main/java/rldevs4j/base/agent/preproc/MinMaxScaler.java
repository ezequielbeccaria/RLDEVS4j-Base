
package rldevs4j.base.agent.preproc;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import rldevs4j.base.env.msg.Step;

/**
 *
 * @author Ezequiel Beccaria
 */
public class MinMaxScaler implements Preprocessing{
    private final double[] minValue;
    private final double[] maxValue;
    private Step lastStep;

    public MinMaxScaler(double[] minValue, double[] maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }    

    @Override
    public boolean input(Step step) {
        for(int i=0;i<step.observationSize();i++){
            step.setFeature(i, normalize(i, step.getFeature(i)));
        }
        lastStep = step;
        return true;
    }
    
    private double normalize(int feature, double value){
        return (value - minValue[feature])/(maxValue[feature] - minValue[feature]);
    }

    @Override
    public Step getState() {
        return lastStep;
    }

    @Override
    public void reset() {
        lastStep = null;
    }

    @Override
    public Preprocessing clone() {
        return new MinMaxScaler(minValue, maxValue);
    }    
}
