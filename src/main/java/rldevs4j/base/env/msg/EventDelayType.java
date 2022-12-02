package rldevs4j.base.env.msg;

import java.util.Random;

/**
 *
 * @author ezequiel
 */
public enum EventDelayType {
    fixed {
        @Override
        public double getDelay(double... values){
            return values[0];
        }
    }, 
    exp{
        @Override
        public double getDelay(double... values){
            return Math.log(1-rand.nextDouble())/(-values[0]);
        }
    }, 
    normal {
        @Override
        public double getDelay(double... values){
            return rand.nextGaussian(values[0], values[1]);
        }
    };
    
    // create random object
    private static Random rand = new Random();
    public abstract double getDelay(double... values);
}