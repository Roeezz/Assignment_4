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
     * Constructs an empty HashTable.
     * @param m2 the size of the hash table.
     */
    public HashTable(String m2){
        this.m2 = Integer.parseInt(m2);
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
        for (Integer badPassword : badPasswords) {
            index = hashFunction(badPassword);
            key = badPassword;
            table[index].addFirst(key);
        }
    }

    /**
     * Runs a search of keys from txt file in given path and gets the time
     * took for the search.
     * @param path the path of the txt file to read the keys from.
     * @return a string containing the search time in milliseconds.
     */
    public String getSearchTime(String path) {
        double startTime = System.nanoTime();
        checkPasswords(path);
        double endTime = System.nanoTime();
        return Double.toString((endTime - startTime)/1000000).substring(0,6);
    }

    /**
     * Receives a key and runs the hash function on it.
     * @param key the key to hash.
     * @return an int that is the hash code of the key.
     */
    private int hashFunction(Integer key){
        return ((5*key + 3) % 15486907) % getM2();
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

    public int getM2() {
        return m2;
    }

    public HashList getTableCell(int i){
        return table[i];
    }

    /**
     * Checks the password against the hash table to see if it's good or bad.
     * @param password a keyed password to check.
     * @return true if the password is not in the table, meaning it's good, otherwise false.
     */
    private boolean checkInTable(Integer password){
        return !getTableCell(hashFunction(password)).contains(password);
    }
}