import static java.lang.Integer.parseInt;

public class BTree {

    //FIELDS

    /**
     * The t constant of the tree.
     */
    private final int T_VAR;
    /**
     * The root of the tree.
     */
    private BTreeNode root;

    //GETTERS AND SETTERS

    /**
     * Constructs an empty BTree.
     *
     * @param tVal the value of the tree's constant.
     */
    public BTree(String tVal) {
        T_VAR = parseInt(tVal);
        root = new BTreeNode(T_VAR);
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void setRoot(BTreeNode root) {
        this.root = root;
    }

    /**
     * Receives a key and inserts it into the root and splitting the roots if it is full.
     *
     * @param key the key to insert to the tree.
     */
    public void insert(String key) {
        if (getRoot().getN() == 2 * T_VAR - 1) {
            splitRoot();
        }
        getRoot().insert(key.toLowerCase());
    }

    /**
     * Splitting the root by creating 2 nodes and copy the median to the node destined to be the new root.
     * Copying the second half of the keys and children to the second node created.
     * Creating two pointers from the new root to the other noes and update their values.
     */
    private void splitRoot() {
        BTreeNode oldRoot = getRoot();
        BTreeNode rightChild = oldRoot.createNodeForSplit(oldRoot);

        if (!oldRoot.isLeaf()) {
            oldRoot.transferChildren(oldRoot, rightChild);
        }

        BTreeNode newRoot = createNewRoot(rightChild);
        oldRoot.setN(T_VAR - 1);
        setRoot(newRoot);
    }

    /**
     * Searches a given key in the tree
     *
     * @param key the key to search
     * @return an ordered pair with the first element being the node in the tree
     * and the second element being the index of the key in the node.
     */
    public OrderedPair search(String key) {
        return getRoot().search(key);
    }

    /**
     * Creates and updates the new root to replace the old one with.
     *
     * @param rightChild the right child of the new root.
     * @return the new updated root.
     */
    private BTreeNode createNewRoot(BTreeNode rightChild) {
        BTreeNode newRoot = new BTreeNode(T_VAR);
        String key = getRoot().getKey(T_VAR - 1);
        newRoot.insert(key);
        newRoot.setChild(0, getRoot());
        newRoot.setChild(1, rightChild);
        newRoot.setLeaf(false);
        return newRoot;
    }

    /**
     * Inserts all keys from txt file in given path to the tree.
     *
     * @param path the path from which to read the keys.
     */
    public void createFullTree(String path) {
        LinkedList<String> passwordsList = UsefulFunctions.createStringListFromFile(path);
        if (passwordsList != null) {
            for (String password : passwordsList) {
                insert(password);
            }
        }
    }

    /**
     * Runs a search of keys from txt file in given path and gets the time
     * took for the search.
     *
     * @param path the path of the txt file to read the keys from.
     * @return a string containing the search time in milliseconds.
     */
    public String getSearchTime(String path) {
        LinkedList<String> keysList = UsefulFunctions.createStringListFromFile(path);
        double startTime = System.nanoTime();
        if (keysList != null) {
            for (String key : keysList) {
                search(key.toLowerCase());
            }
        }
        double endTime = System.nanoTime();
        return Double.toString((endTime - startTime) / 1000000.0).substring(0, 6);
    }

    /**
     * Deletes all keys given in a txt file, if they exist in the tree.
     *
     * @param path the path to read the keys from.
     */
    public void deleteKeysFromTree(String path) {
        LinkedList<String> keysList = UsefulFunctions.createStringListFromFile(path);
        if (keysList != null) {
            for (String key : keysList) {
                delete(key.toLowerCase());
            }
        }
    }

    /**
     * Deletes given key from the tree.
     *
     * @param key the key to delete.
     */
    public void delete(String key) {
        key = key.toLowerCase();
        BTreeNode leftChild = root.getChild(0);
        BTreeNode rightChild = root.getChild(1);
        if (root.getN() == 1 && leftChild.getN() < T_VAR && rightChild.getN() < T_VAR) {
            root = mergeSingleKeyRoot();
        }
        if (root.keyExist(key)) {
            root.handleCase2(key);
        }
        else {
            root.handleCase4(key);
        }
    }

    /**
     * Merges a root with single key with it's two children.
     *
     * @return the merged node
     */
    private BTreeNode mergeSingleKeyRoot() {
        BTreeNode root = getRoot();
        String rootKey = root.getKey(0);
        BTreeNode leftChild = root.getChild(0);
        BTreeNode rightChild = root.getChild(1);
        return root.merge(leftChild, rightChild, rootKey);
    }

    @Override
    public String toString() {
        return getRoot().toString();
    }
}