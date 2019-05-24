import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {
    /**
     * The first element in the list.
     */
    protected Link<T> first;
    protected Link<T> last;

    /**
     * The getSize of the Linked list.
     */
    protected int size;

    /**
     * Constructs an empty LinkedList
     */
    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Gets the amount of elements in the list.
     * @return the value of the field 'size'.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the list has no elements.
     * @return true if the list is empty, otherwise false.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Creates an iterator on the list's elements.
     * @return a LinkedListIterator of the list.
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(first);
    }

    /**
     * Appends a new element as the first element of the list.
     * @param data the data to insert to the element.
     * @throws NullPointerException if given data is null.
     */
    public void addFirst(T data) {
        if(data == null) {
            throw new NullPointerException("Argument is null");
        }
        first = createFirstLink(data);
        if (last == null) {
            last = first;
        }
        size++;
    }

    public void addLast(T data){
        if (data == null){
            throw new NullPointerException("Argument is null");
        }
        if (last == null){
            last = createLastLink(data);
        }
        else {
            last.setNext(createLastLink(data));
        }
        size++;
    }

    protected Link<T> createFirstLink(T data){
        return new Link<>(data, first);
    }
    protected Link<T> createLastLink(T data){
        return new Link<>(data, null);
    }

    /**
     * Searches for the index of the first element with given data, if it exists.
     * @param data the data to search for in the list's elements.
     * @return the index of the searched element if it exists, otherwise -1.
     * @throws NullPointerException if given data is null.
     */
    public int indexOf(Object data)
    {
        if(data == null) {
            throw new NullPointerException("Argument is null");
        }
        int index = 0;
        for(Link<T> current = first; current != null; current = current.getNext())
        {
            if(current.getData().equals(data))
            {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Retrieves the data of the element in th given index.
     * @param index the index to search for.
     * @return the data in the element with the corresponding index.
     * @throws IndexOutOfBoundsException if given index is out of bounds.
     */
    public T get(int index) {
        if(0 > index && index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + " getSize: " + size);
        }
        Link<T> current = first;
        for(int i = 0; i < index; i++){
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Removes the first element with the given data if it exists in the list.
     * @param toRemove the data to search for in the list's elements.
     * @return true if the element was found and removed, otherwise false.
     * @throws NullPointerException if given object is null.
     */
    public boolean remove(Object toRemove) {
        if (toRemove == null){
            throw new NullPointerException("Argument is null");
        }
        Link<T> current = first;
        Link<T> prev = current;
        while(current != null)
        {
            if(current.getData().equals(toRemove))
            {
                if(first.equals(current))
                    first = first.getNext();
                else prev.setNext(current.getNext());
                return true;
            }
            prev = current;
            current = current.getNext();
        }
        return false;
    }

    /**
     * Removes the element with the corresponding index of the given index.
     * @param index a given index form which to remove the element.
     * @return the data of the removed element.
     * @throws IndexOutOfBoundsException if given index is out of bounds.
     */
    public T remove(int index) {
        if(0 > index && index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + " getSize: " + size);
        }
        if(index == 0){
            first = first.getNext();
        }
        Link<T> current = first;
        while(index > 1){
            current = current.getNext();
            index--;
        }
        T output = current.getNext().getData();
        current.setNext(current.getNext().getNext());
        return output;
    }

    /**
     * Checks if an element is in the list/
     * @param element the element to check the list for.
     * @return true if the LinkedList consists the elements and false otherwise.
     */
    public boolean contains(T element) {
        if (element == null) {
            throw new NullPointerException("Argument is null");
        }

        boolean output = false;
        for (Link<T> curr = first; curr != null & !output; curr = curr.getNext()){
            output = curr.getData().equals(element);
        }
        return output;
    }

    @Override
    public String toString() {
        if(isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        Link<T> current = first;
        while(current != null){
            sb.append("=>");
            sb.append(current);
            sb.append("\n");
            current = current.getNext();
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof LinkedList<?>){
            if(isEmpty() && ((LinkedList<?>) other).isEmpty()){
                return first.equals(((LinkedList<?>) other).first);
            }
        }
        return false;
    }
}
