public class DeleteTests {
    public static void main(String[] args) {
        BTreeNode node1 = new BTreeNode(2);
        BTreeNode node2 = new BTreeNode(2);
        BTreeNode node11 = new BTreeNode(2);
        BTreeNode node12 = new BTreeNode(2);
        BTreeNode node21 = new BTreeNode(2);
        BTreeNode node22 = new BTreeNode(2);
        node1.insert("A");
        node2.insert("D");
        node11.insert("a");
        node12.insert("b");
        node21.insert("c");
        node22.insert("d");
        node1.setChild(0, node11);
        node1.setChild(1, node12);
        node2.setChild(0, node21);
        node2.setChild(1, node22);
        node1.setLeaf(false);
        node1.setLeaf(false);
        System.out.println(node1+ " " + node2);
        BTreeNode node3 = node1.merge(node1, node2, "C");
        node3.setLeaf(false);
        System.out.println(node3);
    }
}
