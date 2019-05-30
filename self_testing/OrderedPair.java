import java.util.Arrays;

/**
 * This class implements an ordered pair that holds 2 ordered elements.
 */
public class OrderedPair {
    /**
     * The elements stored in the pair.
     */
    private Object firstElement;
    private Object secondElement;

    public OrderedPair(Object firstElement, Object secondElement){
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public Object getFirstElement() {
        return firstElement;
    }

    public Object getSecondElement() {
        return secondElement;
    }

    public void setFirstElement(Object firstElement) {
        this.firstElement = firstElement;
    }

    public void setSecondElement(Object secondElement) {
        this.secondElement = secondElement;
    }

    @Override
    public String toString() {
        return "Node Subtree: " + firstElement.toString() + "\nIndex: " + secondElement.toString();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof  OrderedPair){
            return firstElement.equals(((OrderedPair) other).firstElement)
                    && secondElement.equals(((OrderedPair) other).secondElement);
        }
        return false;
    }
}
