package rldevs4j.base.env.msg;

import GenCol.entity;

/**     
 * This class represents an event of the environment.  
 * Events could be of two types: 
 * - Controllable event (Action event)
 * - Uncontrollable event (Exogenous event)
 * 
 *  @author Ezequiel Beccaría
 */
public class Event extends entity {
    private final EventType type;
    private final int id;
    private final double delay;
    private final EventDelayType delayType;

    public Event(int id, String name, EventType type) {
        super(name);        
        this.id = id;
        this.type = type;
        this.delay = 0D;
        this.delayType = EventDelayType.fixed;
    }
    
    public Event(int id, String name, EventType type, double delay, EventDelayType delayType) {
        super(name);        
        this.id = id;
        this.type = type;
        this.delay = delay;
        this.delayType = delayType;
    }

    @Override
    public void print() {
        System.out.print(name+"("+id+")");
    }

    public Event copy(){
        return new Event(id, name, type, delay, delayType);
    }
    
    @Override
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }    

    public double getDelay() {
        return this.delayType.getDelay(delay);
    }

    @Override
    public String toString() {
        return "Event{" + "type=" + type + ", id=" + id + ", delay="+ delay +"}";
    }
}
