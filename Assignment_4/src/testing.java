public class testing {
    public static void main (String[] args)
    {
        BTree btree = new BTree("2");
        btree.insert("A");
        btree.insert("B");
        btree.insert("C");
        btree.insert("D");
        btree.insert("L");
        btree.insert("E");
        btree.insert("K");
        btree.insert("J");
        btree.insert("Z");
        btree.insert("X");
        btree.insert("W");
        btree.insert("Q");
        System.out.println(btree);
    }
}
