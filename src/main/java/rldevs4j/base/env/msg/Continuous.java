package rldevs4j.base.env.msg;

/**     
 * Multi-Categorical Event with parametric values of type T.
 * 
 * @author Ezequiel Beccaría
 */
public class Continuous extends Event { 
    private final double[] value;    
    
    public Continuous(int id, String name, EventType type, double[] value) {
        super(id, name, type);
        this.value = value;
        
    }

    public int size(){
        return value.length;
    }

    public double[] getValue() {
        return value;
    }
    
    @Override
    public Continuous copy() {
        return new Continuous(this.getId(), this.name, this.getType(), this.value);
    }
}
