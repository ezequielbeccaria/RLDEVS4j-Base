
package rldevs4j.base.env.gsmdp.evgen;

import GenCol.entity;
import java.util.Map;

/**
 *
 * @author Ezequiel Beccaría
 */
public class ExogenousEventActivation extends entity{
    private Map<String, Map<String,Double>> content;

    public ExogenousEventActivation(Map<String, Map<String,Double>> content) {
        super("ExogenousEventGeneratorEntity");
        this.content = content;        
    }

    public Map<String, Map<String, Double>> getContent() {
        return content;
    }

    public Map<String,Double> getIndividualContent(String generatorName){
        return content.get(generatorName);
    }
    
}
