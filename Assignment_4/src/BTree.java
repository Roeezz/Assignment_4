import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static java.lang.Integer.parseInt;

public class BTree {
    private final int T_VAR;
    private BTreeNode root;

    /**
     * Constructs an empty BTree.
     * @param tVal the value of the tree's constant.
     */
    public BTree(String tVal){
        T_VAR = parseInt(tVal);
        root = new BTreeNode(T_VAR);
    }


    /**
     * Receives a key and inserts it into the root and splitting the roots if it is full.
     * @param key the key to insert to the tree.
     */
    public void insert(String key){
        if(root.getN() == 2 * T_VAR - 1){
            splitRoot();
        }
        root.insert(key);
    }

    /**
     * Splitting the root by creating 2 nodes and copy the median to the node destined to be the new root.
     * Copying the second half of the keys and children to the second node created.
     * Creating two pointers from the new root to the other noes and update their values.
     */
    private void splitRoot() {
        BTreeNode oldRoot = root;
        BTreeNode rightChild = root.createNodeForSplit(root);
        if(!root.isLeaf()){
            root.transferChildren(root, rightChild);
        }
        BTreeNode newRoot = createNewRoot(rightChild);
        oldRoot.setN(T_VAR - 1);
        root = newRoot;
    }

    /**
     * Creates and updates the new root to replace the old one with.
     * @param rightChild the right child of the new root.
     * @return the new updated root.
     */
    private BTreeNode createNewRoot(BTreeNode rightChild) {
        BTreeNode newRoot = new BTreeNode(T_VAR);
        String key = root.getKey(T_VAR - 1);
        newRoot.insert(key);
        newRoot.setChild(0,root);
        newRoot.setChild(1,rightChild);
        newRoot.setLeaf(false);
        return newRoot;
    }

    public void createFullTree(String path) {
        LinkedList<String> passwordsList = UsefulFunctions.createStringListFromFile(path);
        assert passwordsList != null;
        for (String password : passwordsList) {
            insert(password);
        }
    }

    public String getSearchTime(String path) {
        //TODO: implement getSearchTime
        throw new NotImplementedException();
    }

    public void deleteKeysFromTree(String path) {
        //TODO: implement deleteKeysFromTree
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return root.toString();
    }
}