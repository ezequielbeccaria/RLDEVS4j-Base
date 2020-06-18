package rldevs4j.base.env.msg;

/**
 * Categorical Event with a parametric value of type T.
 * 
 * @author Ezequiel Beccaria
 * @param <T>
 */
public class Categorical<T> extends Event{
    private T value;
    
    public Categorical(int id, String name, EventType type, T value) {
        super(id, name, type);
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
