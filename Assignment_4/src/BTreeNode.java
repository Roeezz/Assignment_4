public class BTreeNode {
    //test something

    /**
     * The tree's constant.
     */
    private final int T_VAR;

    /**
     * The number of keys in node.
     */
    private int n;
    private boolean isLeaf;

    /**
     * Array of the keys stored in the node.
     */
    private String[] keys;

    /**
     * Array of pointers to the nodes children
     */
    private BTreeNode[] children;

    /**
     * creates a new node which is a leaf, and creates the children and keys arrays with the t inserted
     * @param t the paramater of the tree
     */
    public BTreeNode(int t){
        this.T_VAR = t;
        isLeaf = true;
        keys = new String[2*t - 1];
        children = new BTreeNode[2*t];
        n = 0;
    }

    public boolean isLeaf(){
        return isLeaf;
    }

    // Getters and Setters
    
    public void setN(int n) {this.n = n;}
    public int getN(){
        return n;
    }

    public String[] getKeys (){return keys;}

    /**
     * Gets the key in given index in the keys array.
     * @param i the index of the key to get.
     * @return a String with the key.
     */
    public String getKey(int i){
        if(i < 0 || i >= n){
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        return keys[i];
    }

    /**
     * Sets the key in given index in the keys array to given key.
     * @param i the index to set.
     * @param str the new key to set the place in the array to to.
     */
    public void setKey(int i, String str) {
        if(i < 0 || i >= n){
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        keys[i]=str;
    }

    /**
     * outputs the node in a specified index
     * @param i index in the children array
     * @return the node located in this index
     */
    public BTreeNode getChild(int i){
        if(i < 0 || i > n){
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        return children[i];
    }

    /**
     * Sets the pointer in given index of the children array to given node.
     * @param i the index of the child to set.
     * @param node the new node to set the child pointer to.
     */
    public void setChild(int i, BTreeNode node) {
        if(i < 0 || i > n){
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        children[i]=node;
    }

    /**
     * Searches for a given key in the tree.
     * @param key the key to search for.
     * @return an ordered pair of a pointer to this node
     * and the index of the key in it's keys array.
     */
    public OrderedPair search(String key){
        int i = 0;
        while(i < n && keys[i].compareTo(key) < 0){
            i++;
        }
        if(i < n && keys[i].equals(key)){
            return new OrderedPair(this, i);
        }
        else if(isLeaf){
            return null;
        }
        else{
            return children[i].search(key);
        }
    }

    /**
     * Inserts given key to it's place in the subtree of this node.
     * @param key the key to insert.
     */
    public void insert(String key){
        // If this node is a leaf then the key is inserted in the right place
        // in the keys array.
        if (isLeaf){
            insertToKeysArray(key);
            n++;
            return;
        }
        // Finds the index of the child to insert the key to.
        int i = 0;
        while(i < n && keys[i].compareTo(key) < 0){
            i++;
        }
        if(children[i].getN() == 2* T_VAR -1){
            splitChild(i);
        }
        children[i].insert(key);
    }

    // Methods related to insert.

    /**
     * Splits the child in given index to 2 children, and puts the middle key
     * of the child between the 2 pointers to the new children.
     * @param index the index of the child to split.
     */
    private void splitChild (int index) {
        BTreeNode splitChild = children[index];
        BTreeNode newChild = createNodeForSplit(splitChild);

        if(!splitChild.isLeaf){
            copyChildren(splitChild, newChild);
        }
        clearPlaceForChild(index, this, newChild);
        setKey(index-1,splitChild.getKey(T_VAR - 1));
        setN(n+1);
        splitChild.setN(T_VAR - 1);

    }

    /**
     * Creates the new right node made from splitting the child, and then copies all
     * keys that come after the median from the split node to the new one.
     * @param splitChild the child node to create the new node from.
     * @return the new right node.
     */
    BTreeNode createNodeForSplit(BTreeNode splitChild) {
        BTreeNode newChild = new BTreeNode(T_VAR);
        newChild.isLeaf = splitChild.isLeaf;
        newChild.setN(T_VAR - 1);
        int index2 = 0; //the index of the keys array of the new node
        for(int i = T_VAR; i < splitChild.getN(); i++) {
            newChild.setKey(index2,splitChild.getKey(i));
            index2++;
        }
        return newChild;
    }

    /**
     * Copies the children's list of the split child from it's median,
     * to the new child's empty children's list.
     * @param splitChild the child being split.
     * @param newChild the new child created from the split.
     */
    void copyChildren(BTreeNode splitChild, BTreeNode newChild) {
        int indexOther = 0;
        for(int i = T_VAR +1; i<splitChild.getN(); i++)
        {
            newChild.setChild(indexOther,splitChild.getChild(i));
            indexOther++;
        }
    }

    /**
     * Clears a place for the new child in the father node's children array,
     * right after the split child.
     * @param index the index of the split child.
     * @param father the father node of the new child and the split child.
     * @param newChild the new child to insert.
     */
    private void clearPlaceForChild(int index, BTreeNode father, BTreeNode newChild) {
        for(int i = father.getN(); i >= i + 1; i--)
        {
            father.setChild(i,father.getChild(i - 1));
        }
        father.setChild(index + 1, newChild);
        for(int i = father.getN()-1; i >= index; i--)
        {
            father.setKey(i,father.getKey(i));
        }
    }

    /**
     * Inserts a key to this nodes keys array.
     * @param key the key to insert.
     */
    private void insertToKeysArray(String key){
        if (key == null){
            throw new NullPointerException();
        }
        int i = n - 1;
        while (i >= 0 && keys[i].compareTo(key) > 0){
            keys[i + 1] = keys[i];
            i--;
        }
        keys[i + 1] = key;
    }
}