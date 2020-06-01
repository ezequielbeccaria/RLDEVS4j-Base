package rldevs4j.base.env.gsmdp.evgen;

import java.util.Map;
import model.modeling.atomic;
import model.modeling.content;
import model.modeling.message;
import rldevs4j.base.env.msg.Event;

/**
 *
 * @author Ezequiel Beccaría
 */
public abstract class ExogenousEventGenerator extends atomic implements Cloneable{
    // Default value used when next random sigma drops below 0.
    protected final double NEGATIVE_SIGMA_CORRECTOR = 0.001; 
    private Event e; //Generated Event
    private final String initPhase;
    private final Double initSigma;

    /**
     * 
     * @param name
     * @param initPhase
     * @param initSigma
     * @param e 
     */
    public ExogenousEventGenerator(String name, Event e, String initPhase, Double initSigma) {
        super(name);        
        this.e = e;
        this.initPhase = initPhase;
        this.phase = initPhase;
        this.initSigma = initSigma;
        
        addInport("in");        
        addOutport("out");   
    }

    @Override
    public void initialize() {        
        holdIn(this.initPhase, initSigma);                                              
    }

    @Override
    public void deltint() {
        holdIn("waiting", nextSigma()); 
    }

    @Override
    public void deltext(double e, message x) {
        for (int i = 0; i < x.getLength(); i++) {
            if (messageOnPort(x, "in", i)) {       
                Map<String, Float> content = ((ExogenousEventActivation)x.getValOnPort("in", i)).getIndividualContent(name);
                if(content.get("active").equals(1D)){                    
                    if(phaseIs("passive")){       
                        holdIn("active", nextSigma());
                    }
                } else if(content.get("active").equals(0D)){
                    passivate();                 
                }    
            }     
        }      
        Continue(e);
    }

    @Override
    public message out() {
        message m = new message();
        if(!phaseIs("passive")){
            content con = makeContent("out", e);
            m.add(con);
        }
        return m;
    }

    public Event getE() {
        return e;
    }
    public void setE(Event e){
        this.e = e;
    }

    public String getInitPhase() {
        return initPhase;
    }

    public Double getInitSigma() {
        return initSigma;
    }
    
    public abstract double nextSigma();
    public abstract ExogenousEventGenerator clone();
    
}
