import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HashTableAndBloomFilterTest {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable("32");
        BloomFilter bloomFilter = new BloomFilter("32",
                System.getProperty("user.dir")+"/hash_functions.txt");

        hashTable.updateTable(System.getProperty("user.dir")+"/bad_passwords.txt");
        bloomFilter.updateTable(System.getProperty("user.dir")+"/bad_passwords.txt");
        writeToOutput(hashTable, bloomFilter);
    }

    private static void writeToOutput(HashTable table, BloomFilter filter){
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getFalsePositivePercentage(table, System.getProperty("user.dir") + "/requested_passwords.txt"));
        sb.append(System.lineSeparator());
        sb.append(filter.getRejectedPasswordsAmount( System.getProperty("user.dir") + "/requested_passwords.txt"));
        byte[] strToBytes = sb.toString().getBytes();
        try {
            Files.write(Paths.get(System.getProperty("user.dir") + "/output.txt"), strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
