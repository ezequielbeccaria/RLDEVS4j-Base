package rldevs4j.base.env.msg;

import GenCol.entity;
import java.lang.*;
import java.util.Objects;

/**     
 * This class represents an event of the environment.  
 * Events could be of two types: 
 * - Controllable event (Action event)
 * - Uncontrollable event (Exogenous event)
 * 
 *  @author Ezequiel Beccaría
 */
public class Event extends entity {
    private EventType type;
    private int id;
    private double value;

    public Event(int id, String name, EventType type, double value) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(name+": "+value);
    }

    public Event copy() {
        return new Event(this.id, this.name, this.type, this.value);
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
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + this.id;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
    
    
}
