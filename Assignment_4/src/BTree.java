import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

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
        BTreeNode rightChild = root.createNodeForSplit(root);
        if(!root.isLeaf()){
            root.transferChildren(root, rightChild);
        }

        BTreeNode newRoot = new BTreeNode(T_VAR);
        String key = root.getKey(T_VAR - 1);
        newRoot.insert(key);
        newRoot.setChild(0,root);
        newRoot.setChild(1,rightChild);
        root.setN(T_VAR - 1);

        root = newRoot;
        root.setLeaf(false);
    }

    public void createFullTree(String s) {
        //TODO: implement createFullTree
        throw new NotImplementedException();
    }

    public String getSearchTime(String s) {
        //TODO: implement getSearchTime
        throw new NotImplementedException();
    }

    public void deleteKeysFromTree(String s) {
        //TODO: implement deleteKeysFromTree
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return root.toString();
    }
}