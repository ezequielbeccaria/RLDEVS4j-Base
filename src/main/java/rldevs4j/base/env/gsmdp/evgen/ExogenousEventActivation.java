
package rldevs4j.base.env.gsmdp.evgen;

import GenCol.entity;
import java.util.Map;

/**
 *
 * @author Ezequiel Beccaría
 */
public class ExogenousEventActivation extends entity{
    private Map<String, Map<String,Float>> content;

    public ExogenousEventActivation(Map<String, Map<String,Float>> content) {
        super("ExogenousEventGeneratorEntity");
        this.content = content;        
    }

    public Map<String, Map<String, Float>> getContent() {
        return content;
    }

    public Map<String,Float> getIndividualContent(String generatorName){
        return content.get(generatorName);
    }
    
}
