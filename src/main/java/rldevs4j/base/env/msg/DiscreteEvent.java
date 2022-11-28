
package rldevs4j.base.env.msg;

/**
 *
 * @author Ezequiel Beccaria
 */
public class DiscreteEvent extends Event{
    private int value;

    public DiscreteEvent(int id, String name, EventType type, int value) {
        super(id, name, type);
        this.value = value;
    }
    
    public DiscreteEvent(int id, String name, EventType type) {
        super(id, name, type);
        this.value = 0;
    }
    
    @Override
    public Event copy() {
        return new DiscreteEvent(getId(), getName(), getType(), value);
    }
    
}
