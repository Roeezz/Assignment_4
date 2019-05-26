public class BTreeNode {
    //FIELDS
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
     *
     * @param t the paramater of the tree
     */
    public BTreeNode(int t) {
        this.T_VAR = t;
        isLeaf = true;
        keys = new String[2 * t - 1];
        children = new BTreeNode[2 * t];
        n = 0;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    // Getters and Setters
    public void setN(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String[] getKeys() {
        return keys;
    }

    /**
     * Gets the key in given index in the keys array.
     *
     * @param i the index of the key to get.
     * @return a String with the key.
     */
    public String getKey(int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        return keys[i];
    }

    /**
     * Sets the key in given index in the keys array to given key.
     *
     * @param i   the index to set.
     * @param str the new key to set the place in the array to to.
     */
    private void setKey(int i, String str) {
        if (i < 0 || i > n) {
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        keys[i] = str;
    }

    /**
     * outputs the node in a specified index
     *
     * @param i index in the children array
     * @return the node located in this index
     */
    public BTreeNode getChild(int i) {
        if (i < 0 || i > n) {
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        return children[i];
    }

    /**
     * Sets the pointer in given index of the children array to given node.
     *
     * @param i    the index of the child to set.
     * @param node the new node to set the child pointer to.
     */
    public void setChild(int i, BTreeNode node) {
        if (i < 0 || i > n) {
            throw new IllegalArgumentException("Index: " + i + " n: " + n);
        }
        children[i] = node;
    }

    //Search, insert and remove.

    /**
     * Searches for a given key in the tree.
     *
     * @param key the key to search for.
     * @return an ordered pair of a pointer to this node
     * and the index of the key in it's keys array.
     */
    public OrderedPair search(String key) {
        int i = findExpectedIndexOfKey(key);
        if (i < n && keys[i].equals(key)) {
            return new OrderedPair(this, i);
        } else if (isLeaf) {
            return null;
        } else {
            return children[i].search(key);
        }
    }

    /**
     * Inserts given key to it's place in the subtree of this node.
     *
     * @param key the key to insert.
     */
    public void insert(String key) {
        // If this node is a leaf then the key is inserted in the right place
        // in the keys array.
        if (isLeaf) {
            insertToKeysArray(key);
            return;
        }
        int i = findExpectedIndexOfKey(key);
        // If the node is full we split it.
        if (children[i].getN() == 2 * T_VAR - 1) {
            splitChild(i);
        }
        insertToCorrectChild(key, i);
    }

    /**
     * Finds the expected index of the keys if it was in the current node's keys array.
     * This helps to find the index of the child in which the key should be.
     *
     * @param key the key this searches for.
     * @return the index of key, or the one it would have if it was in the keys array.
     */
    private int findExpectedIndexOfKey(String key) {
        int i = 0;
        while (i < n && keys[i].compareTo(key) < 0) {
            i++;
        }
        return i;
    }

    // Methods related to insert.

    /**
     * Splits the child in given index to 2 children, and puts the middle key
     * of the child between the 2 pointers to the new children.
     *
     * @param index the index of the child to split.
     */
    private void splitChild(int index) {
        BTreeNode splitChild = children[index];
        BTreeNode newChild = createNodeForSplit(splitChild);

        if (!splitChild.isLeaf) {
            transferChildren(splitChild, newChild);
        }
        insertMedianKey(index, this, splitChild);
        insertNewChild(index, this, newChild);
        splitChild.setN(T_VAR - 1);
    }

    /**
     * Sub method of insert that inserts the median key of the split child
     * to it's right place in the father node
     *
     * @param index      the index to insert the key to
     * @param father     the father node of the split child
     * @param splitChild the split child.
     */
    private void insertMedianKey(int index, BTreeNode father, BTreeNode splitChild) {
        for (int i = father.getN(); i > index; i--) {
            father.setKey(i, this.getKey(i - 1));
        }
        setKey(index, splitChild.getKey(T_VAR - 1));
        setN(n + 1);
    }

    /**
     * Creates the new right node made from splitting the child, and then copies all
     * keys that come after the median from the split node to the new one.
     *
     * @param splitChild the child node to create the new node from.
     * @return the new right node.
     */
    BTreeNode createNodeForSplit(BTreeNode splitChild) {
        BTreeNode newChild = new BTreeNode(T_VAR);
        newChild.isLeaf = splitChild.isLeaf;
        newChild.setN(T_VAR - 1);
        int index2 = 0; //the index of the keys array of the new node
        int splitN;
        for (int i = T_VAR; i < splitChild.getN(); i++) {
            newChild.setKey(index2, splitChild.getKey(i));
            splitChild.setKey(i, null);
            index2++;
        }
        return newChild;
    }

    /**
     * Copies the children's list of the split child from it's median,
     * to the new child's empty children's list.
     *
     * @param splitChild the child being split.
     * @param newChild   the new child created from the split.
     */
    void transferChildren(BTreeNode splitChild, BTreeNode newChild) {
        int indexOther = 0;
        for (int i = T_VAR; i <= 2 * T_VAR - 1; i++) {
            newChild.setChild(indexOther, splitChild.getChild(i));
            splitChild.setChild(i, null);
            indexOther++;
        }
    }

    /**
     * Sub method of insert that clears a place for the new child in the father
     * node's children array, right after the split child.
     *
     * @param index    the index of the split child.
     * @param father   the father node of the new child and the split child.
     * @param newChild the new child to insert.
     */
    private void insertNewChild(int index, BTreeNode father, BTreeNode newChild) {
        for (int i = father.getN(); i > index; i--) {
            father.setChild(i, father.getChild(i - 1));
        }
        father.setChild(index + 1, newChild);
    }

    /**
     * Sub method of insert that inserts a key to this nodes keys array.
     *
     * @param key the key to insert.
     */
    private void insertToKeysArray(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        int i = n - 1;
        while (i >= 0 && keys[i].compareTo(key) > 0) {
            keys[i + 1] = keys[i];
            i--;
        }
        keys[i + 1] = key;
        setN(n + 1);
    }

    /**
     * Sub method of insert used to decide on which of the 2 new children
     * to call the recursive insertion on.
     *
     * @param key the key to insert.
     * @param i   the index of the key between the 2 new children.
     */
    private void insertToCorrectChild(String key, int i) {
        if (i == n || key.compareTo(keys[i]) < 0) {
            children[i].insert(key);
        } else {
            children[i + 1].insert(key);
        }
    }

    //toString
    @Override
    public String toString() {
        return toString(new StringBuilder(), 0).toString();
    }

    /**
     * Sub method of toString that builds the string representation
     * of the subtree recursively.
     *
     * @param sb    an accumulator that collects the subtree's toStrings
     * @param depth the depth of the current subtree.
     * @return a string visually representing the subtree.
     */
    private StringBuilder toString(StringBuilder sb, int depth) {
        if (isLeaf()) {
            return sb.append(addKeysToString(depth));
        }
        //Gets the string representation of the sub tree of this node.
        for (int i = 0; i <= n; i++) {
            sb = children[i].toString(sb, depth + 1);
            if (i < n) {
                sb.append(keys[i]).append("_").append(depth).append(",");
            }
        }
        return sb;
    }

    /**
     * Sub method of toString used to addFirst the keys held in the current node.
     *
     * @param depth the depth of the current node.
     * @return a string with the keys and the depth they are from.
     */
    private String addKeysToString(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(keys[i]).append("_").append(depth).append(",");
        }
        return sb.toString();
    }

    public void delete(String key, BTreeNode father) {
        if (n < T_VAR) {
            handleCase1();
        }
        boolean keyExist = searchKey(key);
        if (keyExist && !isLeaf) {
            handleCase2();
        }
        else if (keyExist && isLeaf) {
            deleteKey(key); //case 3
            return;
        }
        else { //Key not in the node
            handleCase4(key);
        }
    }

    private boolean searchKey(String key) {
        return UsefulFunctions.binarySearch(keys, key) != -1;
    }

    private void handleCase1() {
        //TODO: Implement handleCase1
    }

    private void handleCase2() {
        //TODO: Implement handleCase2

    }


    /**
     * This functions handles the case in which the key is not in the current node.
     * We check where to continue searching for it.
     *
     * @param key to check in which child it might be in
     */
    private void handleCase4(String key) {

        for (int i = 0; i < getN(); i++) {
            String keyCheck = getKey(i);
            if (keyCheck.compareTo(key) > 0) //keyCheck > key
            {
                delete(key, getChild(i));
            }
        }
        delete(key, getChild(getN()));
    }

    public void deleteKey(String key) {
        int index = (int) search(key).getSecondElement();
        if (n - index >= 0) System.arraycopy(keys, index + 1, keys, index, n - index);
        setN(n - 1);
    }

}
