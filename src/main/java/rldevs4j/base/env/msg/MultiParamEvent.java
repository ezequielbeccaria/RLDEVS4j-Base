package rldevs4j.base.env.msg;

/**     
 * This class represents an event of the environment.  
 * Events could be of two types: 
 * - Controllable event (Action event)
 * - Uncontrollable event (Exogenous event)
 * 
 * This class of event can have zero, one or more parameters.
 * 
 *  @author Ezequiel Beccaría
 */
public class MultiParamEvent extends Event { 
    private Object[] params;
    
    public MultiParamEvent(int id, String name, EventType type, Object... values) {
        super(id, name, type, 0D);
        this.params = values;
    }
    
    public int paramSize(){
        return params.length;
    }
    
    public Object[] getParams(){
        return params;
    }
    
    public Object getParam(int idx){
        if (idx>=0 && idx<params.length)
            return params[idx];
        return null;
    }
}
