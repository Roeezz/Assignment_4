public class BtreeInsertTest {
    public static void main (String[] args)
    {
        BTree btree = new BTree("2");
        btree.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
//        btree.insert("J");
//        btree.insert("B");
//        btree.insert("D");
//        btree.insert("L");
//        btree.insert("A");
//        btree.insert("E");
//        btree.insert("K");
//        btree.insert("C");
//        btree.insert("Z");
//        btree.insert("X");
//        btree.insert("W");
//        btree.insert("Q");
        System.out.println(btree);
    }
}
