public class HashList extends LinkedList<Integer> {

    /**
     * Constructs a new empty HashList.
     */
    public HashList(){
        super();
    }

    @Override
    public HashListElement createFirstLink(Integer data) {
        return new HashListElement(data, (HashListElement) first);
    }

    @Override
    protected HashListElement createLastLink(Integer data){
        return new HashListElement(data,null);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof HashList)
            return super.equals(other);
        return false;
    }

}
