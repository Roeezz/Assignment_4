public class DeleteTests {
    public static void main(String[] args) {
        BTreeNode node = new BTreeNode(5);
        node.insert("A");
        node.insert("B");
        node.insert("C");
        node.insert("D");
        node.insert("E");
        node.insert("F");
        System.out.println(node);
        node.deleteKey("C");
        System.out.println(node);
        node.deleteKey("F");
        System.out.println(node);
        node.deleteKey("A");
        System.out.println(node);
    }
}
