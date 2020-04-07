package rldevs4j.base.env.msg;

/**     
 * Multi-Categorical Event with parametric values of type T.
 * 
 * @author Ezequiel Beccaría
 * @param <T>
 */
public class MultCategorical<T> extends Event { 
    private final T[] values;
    
    public MultCategorical(int id, String name, EventType type, T... values) {
        super(id, name, type);
        this.values = values;
    }

    public int size(){
        return values.length;
    }

    public T[] getValues() {
        return values;
    }
}
