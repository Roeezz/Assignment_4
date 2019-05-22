import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.ByteBuffer;

public class HashTable {
    /**
     * An array representing the table to which we chain the HashLists.
     */
    private HashList[] table;
    /**
     * The size of the hashtable.
     */
    private int m2;
    /**
     * Constants for the hash function.
     */
    private static final int FNV_32_INIT = 0x811c9dc5;
    private static final int FNV_32_PRIME = 0x01000193;

    /**
     * Constructs an empty HashTable.
     * @param m2 the size of the hash table.
     */
    public HashTable(String m2){
        this.m2 = (int)UsefulFunctions.createNumberWithGivenBase(m2,10);
        table = new HashList[this.m2];
        for(int i = 0; i < table.length; i++){
            table[i] = new HashList();
        }
    }

    /**
     * Updates the table using values read from a txt file.
     * @param path the path from which to read the values.
     */
    public void updateTable(String path) {
        LinkedList<Integer> badPasswords = UsefulFunctions.getKeysList(path);
        int index; Integer key;
        System.out.println(badPasswords);
        for (Integer badPassword : badPasswords) {
            index = hashFunction(badPassword);
            key = badPassword;
            table[index].add(key);
        }
    }

    /**
     * TODO: document getSearchTime.
     * @param path
     * @return
     */
    public String getSearchTime(String path) {
        //TODO: implement getSearchTime
        throw new NotImplementedException();
    }

    /**
     * Receives a key and runs the hash function on it.
     * @param key the key to hash.
     * @return an int that is the hash code of the key.
     */
    public int hashFunction(Integer key){
        return ((5*key + 3) % 15486907) % m2;
//        byte[] bytes = ByteBuffer.allocate(4).putInt(key).array();
//        return hashFunction(bytes);
    }

    /**
     * Hashes a byte array into an index in m2
     * @param data a byte array representing the data to hash.
     * @return an int that is the hashcode of the data.
     */
    public int hashFunction(byte[] data){
        long rv = FNV_32_INIT;
        for (int i = 0; i < data.length; i++) {
            byte datum = data[i];
            rv ^= datum;
            rv *= FNV_32_PRIME;
        }
        return (int) rv % m2;
    }

    /**
     * Checks a list of passwords against the hash table to see if they are
     * good or bad.
     * @param path the path of the file containing the passwords.
     * @return a boolean array, in which a true element means the password is good.
     */
    public boolean[] checkPasswords(String path){
        LinkedList<Integer> keysList = UsefulFunctions.getKeysList(path);
        boolean[] results = new boolean[keysList.getSize()];
        int index = 0;
        for (Integer password : keysList) {
            results[index] = checkInTable(password);
            index++;
        }
        return results;
    }

    /**
     * Checks the password against the hash table to see if it's good or bad.
     * @param password a keyed password to check.
     * @return true if the password is not in the table, meaning it's good, otherwise false.
     */
    public boolean checkInTable(Integer password){
        return !table[hashFunction(password)].contains(password);
    }
}