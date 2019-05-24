public class CheckTimeTests {
    public static void main(String[] args) {
        BTree bTree = new BTree("2");
        HashTable hashTable = new HashTable("32");
        hashTable.updateTable(System.getProperty("user.dir")+"/bad_passwords.txt");
        bTree.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
        System.out.println(hashTable.getSearchTime(System.getProperty("user.dir")+"/requested_passwords.txt"));
        System.out.println(bTree.getSearchTime(System.getProperty("user.dir")+"/requested_passwords.txt"));
    }
}
