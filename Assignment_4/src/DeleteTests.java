public class DeleteTests {
    public static void main(String[] args) {
        BTree tree = new BTree("2");
        tree.createFullTree(System.getProperty("user.dir") + "/baspass.txt");
        System.out.println(tree);
        LinkedList<String> deletes = UsefulFunctions.createStringListFromFile(System.getProperty("user.dir")
                + "/delete.txt");
        for (String delete : deletes) {
            tree.delete(delete);
            System.out.println(delete);
        }
        System.out.println(tree);
    }

}
