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

    public Event(int id, String name, EventType type) {
        super(name);        
        this.id = id;
        this.type = type;
    }

    @Override
    public void print() {
        System.out.print(name+"("+id+")");
    }

    public Event copy(){
        return new Event(id, name, type);
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

    @Override
    public String toString() {
        return "Event{" + "type=" + type + ", id=" + id + '}';
    }
}
