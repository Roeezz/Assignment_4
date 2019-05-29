public class DeleteTests {
    public static void main(String[] args) {
        BTree tree = new BTree("2");
        tree.insert("W");
        tree.insert("R");
        tree.insert("M");
        tree.insert("L");
        tree.insert("A");
        tree.insert("Z");
        tree.insert("P");
        System.out.println(tree);
        tree.delete("J");
        System.out.println(tree);
    }

}
