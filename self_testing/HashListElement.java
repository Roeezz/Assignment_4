public class HashListElement extends Link<Integer> {

    /**
     * Constructs a new HashListElement with given data.
     * @param data the data to store in the element.
     * @param next the next element to point to.
     */
    public HashListElement(Integer data, HashListElement next) {
        super(data, next);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof HashListElement)
            return super.equals(other);
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
