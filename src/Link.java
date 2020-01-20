public class Link <T> {
    /**
     * A pointer to the next link in the list.
     */
    private Link<T> next;

    /**
     * The data stored inside the link.
     */
    private T data;

    /**
     * Constructs a new link.
     * @param data the data to store in the link.
     * @param next the next link to point to.
     * @throws NullPointerException if the given data is null.
     */
    public Link(T data, Link<T> next) {
        if(data == null){
            throw new NullPointerException("Argument is null");
        }
        this.data = data;
        this.next = next;
    }

    /**
     * Sets the data of the link to given data.
     * @param data the data to replace the current data with.
     * @throws NullPointerException if the given data is null.
     */
    public void setData(T data) {
        if(data == null){
            throw new NullPointerException("Argument is null");
        }
        this.data = data;
    }

    /**
     * Sets the next link to a given link.
     * @param next the link to point to.
     */
    public void setNext(Link<T> next)
    {
        this.next = next;
    }

    /**
     * Gets the next link in the list.
     * @return the link that 'next' points to.
     */
    public Link<T> getNext()
    {
        return next;
    }

    /**
     * Gets the data stored in the link.
     * @return the data stored in 'data'.
     */
    public T getData()
    {
        return data;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Link<?>)
            return getData().equals(((Link<?>) other).getData()) && getNext().equals(((Link<?>) other).getNext());
        return false;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
