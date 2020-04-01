package rldevs4j.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Circular ArrayList<E> that allow left and rigth access to the elemets in a 
 * python way. Furthemore, implement getNext that return elements in circular way.
 * This is, if last getted element was size()-1, then the next element returned 
 * will be the first element of the list.
 * @author Ezequiel Beccaría
 * @param <E>
 */
public class CircularArrayList<E> extends ArrayList<E>{
    private int nextIdx;

    public CircularArrayList(int initialCapacity) {
        super(initialCapacity);
        this.nextIdx = 0;
    }

    public CircularArrayList() {
        super();
        this.nextIdx = 0;
    }

    public CircularArrayList(Collection<? extends E> c) {
        super(c);
        this.nextIdx = 0;
    }
    
    public CircularArrayList(E[] c) {
        super(c.length);
        for(int i=0;i<c.length;i++)
            this.add(c[i]);
        this.nextIdx = 0;
    }
    
    @Override
    public E get(int index) {
        if (index == -1) {
            index = size()-1;
        } else if (index == size()) {
            index = 0;
        }
        return super.get(index);
    }
  
    /**
     * Get the next element of the array starting for the first element.
     * When the last element was getted, then the next element will be again the
     * first element.
     * @return 
     */
    public E getNext(){
        E nextElement = this.get(this.nextIdx);
        if(this.nextIdx<this.size())
            this.nextIdx++;
        else
            this.nextIdx = 0;
        return nextElement;
    }
}
