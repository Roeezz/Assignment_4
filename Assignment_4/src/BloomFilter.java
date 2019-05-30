import java.util.Arrays;

public class BloomFilter {
    /**
     * An array that represents the bloom filter.
     */
    private boolean[] bloomFilter;
    /**
     * The size of the filter.
     */
    private int m1;
    /**
     * The list of hash functions to hash the keys with
     */
    private HashFunctionsList hashFunctionsList;

    /**
     *  Constructs a new blank bloom filter.
     * @param m1 the size of the bloom filter.
     * @param path the path from which to read the hash functions parameters.
     * @throws NullPointerException if a given parameter is null.
     */
    BloomFilter(String m1, String path){
        if(m1 == null || path == null){
            throw new NullPointerException("An argument is null");
        }
        hashFunctionsList = new HashFunctionsList(path);
        this.m1 = Integer.parseInt(m1);
        bloomFilter = new boolean[this.m1];
    }

    /**
     * Updates the bloom filter with given passwords.
     * @param path the path from which to read the functions.
     */
    public void updateTable(String path) {
        LinkedList<String> badPassStringList = UsefulFunctions.createStringListFromFile(path);
        LinkedList<Integer> badPassKeysList = UsefulFunctions.convertToKeys(badPassStringList);
        updateFilter(badPassKeysList);
    }

    /**
     * Updates the bloom filter with given keys.
     * @param badPassKeysList the list fo the keys to update to the filter.
     */
    private void updateFilter(LinkedList<Integer> badPassKeysList)
    {
        for(int i = 0; i< badPassKeysList.getSize(); i++)
        {
            Integer k = badPassKeysList.get(i);
            for(HashFunction func: getHashFunctionsList()) {
                int index = func.runFunction(k,getM1());
                setFilterPosition(index,true);
            }
        }
    }

    /**
     * Checks a list of passwords against the bloom filter to see if they are
     * good or bad.
     * @param path the path of the file containing the passwords.
     * @return a boolean array, in which a true element means the password is good.
     */
    private boolean[] checkPasswords(String path){
        LinkedList<Integer> keysList = UsefulFunctions.getKeysList(path);
        int index = 0;
        boolean[] results = new boolean[keysList.getSize()];
        for (Integer requestedPassKey : keysList) {
            results[index] = checkAgainstFilter(requestedPassKey);
            index++;
        }
        return results;
    }

    /**
     * Checks the password against the bloom filter to see if it's good or bad.
     * @param password a keyed password to check.
     * @return true if the password is not in the bloom filter, meaning it's good, otherwise false.
     */
    private boolean checkAgainstFilter(Integer password){
        int index;
        for(HashFunction function: getHashFunctionsList()){
            index = function.runFunction(password,getM1());
            if(!getFilterPosition(index))
                return true;
        }
        return false;
    }

    /**
     * Gets the of the filter ratio of false positive.
     * @param hashTable a hash table with the bad passwords.
     * @param path the path from which to read the requested passwords we want to check.
     * @return A String with the number representing the false positive ratio.
     */
    public String getFalsePositivePercentage(HashTable hashTable, String path) {
        boolean[] trueBadPasswords = hashTable.checkPasswords(path);
        boolean[] filteredPasswords = checkPasswords(path);
        Double goodPasswords = goodPasswords(trueBadPasswords);
        Double falsePositives = falsePositives(trueBadPasswords,filteredPasswords);
        double ratio = falsePositives / goodPasswords;
        return Double.toString(ratio);
    }

    /**
     * Gets the correct type of passwords from the hash table and checks how many true good passowrds there are
     * @param trueBadPasswords an array which in every index has true if the password is good and false otherwise
     * @return The correct amount of good passwords
     */
    private double goodPasswords(boolean[] trueBadPasswords){
        int counter = 0;
        for (boolean trueBadPassword : trueBadPasswords) {
            if (trueBadPassword){
                counter++;
            }
        }
        return counter;
    }

    /**
     * for each password checks if it is good and the bloom filter calculated it as bad (=false positives).
     * @param trueBadPasswords has the correct type of each password - true if its good and false if its bad.
     * @param filteredBadPasswords has the type that the bloom filter calculated.
     * @return the amount of false-positives.
     */
    private double falsePositives(boolean[] trueBadPasswords, boolean[] filteredBadPasswords){
        int counter = 0;
        for (int i = 0; i < trueBadPasswords.length; i++) {
            if(trueBadPasswords[i] & !filteredBadPasswords[i]){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Receives a path, and checks how many passwords are counted as bad.
     * @param path the path of the file from which the function reads the passwords.
     * @return the amount of passwords counted as bad in the bloom filter (rejected passwords).
     */
    public String getRejectedPasswordsAmount(String path) {
        boolean[] filteredPasswords = checkPasswords(path);
        int rejected = 0;
        for (boolean filteredPassword : filteredPasswords) {
            if(!filteredPassword){
                rejected++;
            }
        }
        return Integer.toString(rejected);
    }

    // GETTERS AND SETTERS

    public boolean getFilterPosition(int i) {
        return bloomFilter[i];
    }

    public void setFilterPosition(int i, boolean exist){
        bloomFilter[i] = exist;
    }

    public boolean[] getBloomFilter() {
        return bloomFilter;
    }

    public HashFunctionsList getHashFunctionsList() {
        return hashFunctionsList;
    }

    public int getM1() {
        return m1;
    }

    //EQUALS AND toSTRING
    @Override
    public String toString() {
        return Arrays.toString(getBloomFilter());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BloomFilter){
            return Arrays.equals(getBloomFilter(), ((BloomFilter) obj).getBloomFilter()) &&
                    getHashFunctionsList().equals(((BloomFilter) obj).getHashFunctionsList());
        }
        return false;
    }
}