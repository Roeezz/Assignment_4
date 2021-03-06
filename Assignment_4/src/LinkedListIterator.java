import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator <T> implements Iterator<T> {
    /**
     * The current element the iterator points to.
     */
    private Link<T> current;

    /**
     * Constructs a new iterator on a linked list.
     * @param first the element to start the iterator from.
     */
    public LinkedListIterator(Link<T> first){
        current = first;
    }

    /**
     * Checks if the iterator has more elements.
     * @return true if there is a next element, otherwise false.
     */
    @Override
    public boolean hasNext() {
        return getCurrent() != null;
    }

    /**
     * Advances the iterator to the next element while returning the current one.
     * @return the current elements data.
     */
    @Override
    public T next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }

        T next = getCurrent().getData();
        setCurrent(getCurrent().getNext());
        return next;
    }

    //GETTERS AND SETTERS

    public Link<T> getCurrent() {
        return current;
    }

    public void setCurrent(Link<T> current) {
        this.current = current;
    }
}
