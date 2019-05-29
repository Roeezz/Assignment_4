import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UsefulFunctions {
    /**
     * Creates a String LinkedList with elements from a .txt file in the path.
     * @param path the path from which to read the elements.
     * @return LinkedList of Integer keys of the strings in the paths.
     */
    public static LinkedList<Integer> getKeysList(String path){
        LinkedList<String> requestedPasswords = UsefulFunctions.createStringListFromFile(path);
        return UsefulFunctions.convertToKeys(requestedPasswords);
    }

    /**
     * runs the binarySearch function on a sorted array and a key in all of the array.
     * @param array the array to search the key in.
     * @param key the key to search.
     * @return its index if the key is in the array and -1 otherwise.
     */
    public static int binarySearch(String[] array, String key, int size){
        return binarySearch(array, key, 0, size - 1);
    }

    /**
     * Receives a sorted array of strings and checks if a string
     * is in the array using binary search algorithm.
     * @param array of strings to search the key in.
     * @param left the left end of the search area.
     * @param right the right end of the search area.
     * @param key the keay to search
     * @return its index if the key is in the array and -1 otherwise.
     */
    private static int binarySearch(String[] array, String key, int left, int right) {
        if(right>=left)
        {
            int mid = (left+right)/2;
            if(array[mid].compareTo(key) == 0)
            {
                return  mid;

            }
            else if (array[mid].compareTo(key) > 0)
            {
                return binarySearch(array,key,left,mid-1);
            }
            return binarySearch(array, key,mid + 1, right);

        }
        return -1;
    }

    /**
     * Receives a path to a .txt file and creates a LinkedList of strings containing the strings in the file.
     * @param path the from which to read the strings.
     * @return a LinkedList of Strings.
     */
    public static LinkedList<String> createStringListFromFile(String path) {
        if(path == null){
            throw new NullPointerException("Argument is null");
        }
        LinkedList<String> stringList = new LinkedList<>();
        File file = new File(path);
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                stringList.addLast(line);
            }
            return stringList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Converts a LinkedList of strings into a LinkedList of Integer keys using horner's rule
     * @param listS a list of strings to convert to keys.
     * @return a LinkedList of Integer keys
     */
    public static LinkedList<Integer> convertToKeys(LinkedList<String> listS) {
        if (listS == null) {
            throw new NullPointerException("Argument is null");
        }
        String pass;
        int length; int ascii; int[] polynomial;

        LinkedList<Integer> keysList = new LinkedList<>();
        for(int i = 0; i < listS.getSize(); i++) {
            pass = listS.get(i);
            length = pass.length();
            polynomial = new int[length];
            for(int j = 0; j < pass.length(); j++) {
                ascii = (int) pass.charAt(j);
                polynomial[j] = ascii;
            }

            keysList.addLast(horner(polynomial,length));
        }
        return keysList;
    }

    /**
     * Receives an array with coefficients and value and calculates.
     * @param polynomial an int array holding the ascii values of the letters.
     * @param wordLength the length of the word converted to base 10.
     * @return the outcome of the polynomial using horner's rule.
     */
    private static int horner(int[] polynomial, int wordLength) {
        int p = 15486907;
        long result = polynomial[0];
        for(int i = 1; i < wordLength; i++) {
            result = (((result) % p)* 256) % p + polynomial[i];
        }
        return (int)result;
    }
}