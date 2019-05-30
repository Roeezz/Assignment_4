public class BloomFilterTests {
        public static void main (String[] args)
        {
            BloomFilter yoshi = new BloomFilter("30", System.getProperty("user.dir")+ "/hash_functions.txt");
            yoshi.updateTable(System.getProperty("user.dir")+ "/bad_passwords.txt");
            System.out.println(yoshi);
        }
}
