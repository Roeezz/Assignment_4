public class CheckTimeTests {
    public static void main(String[] args) {
        BTree bTree = new BTree("2");
        bTree.createFullTree(System.getProperty("user.dir")+"/bad_passwords.txt");
        System.out.println(bTree);
//        System.out.println(bTree.getSearchTime(System.getProperty("user.dir")+"/requested_passwords.txt"));
    }
}
