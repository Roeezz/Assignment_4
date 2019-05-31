import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class HashFunctionsList implements Iterable<HashFunction> {
    /**
     * A linked list of the hash functions.
     */
    private LinkedList<HashFunction> hashFunctionsList;

    public HashFunctionsList(String path) {
        createFunctionsList(path);
    }

    /**
     * Creates a list of hash functions with the parameters read from a txt file.
     *
     * @param path the specified path of the txt file.
     * @throws NullPointerException if given path is null.
     */
    private void createFunctionsList(String path) {
        if (path == null) {
            throw new NullPointerException("Argument is null");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            hashFunctionsList = new LinkedList<>();
            String function;
            String[] functionVariables;
            int alpha, beta;

            while ((function = reader.readLine()) != null) {
                functionVariables = function.split("_");
                alpha = Integer.parseInt(functionVariables[0]);
                beta = Integer.parseInt(functionVariables[1]);
                hashFunctionsList.addFirst(createHashFunction(alpha, beta));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the amount of functions of the list.
     *
     * @return the size of hashFunctionsList.
     */
    public int getSize() {
        return hashFunctionsList.getSize();
    }

    /**
     * Creates a HashFunctions with given parameters.
     *
     * @param alpha a parameter used to calculate the function.
     * @param beta  a parameter used to calculate the function.
     * @return a HashFunction.
     */
    private HashFunction createHashFunction(int alpha, int beta) {
        return new HashFunction(alpha, beta);
    }

    /**
     * Creates an iterator on the hash functions list.
     *
     * @return an iterator of the list.
     */
    @Override
    public Iterator<HashFunction> iterator() {
        return hashFunctionsList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof HashFunctionsList) return hashFunctionsList.equals(other);
        return false;
    }

    @Override
    public String toString() {
        return hashFunctionsList.toString();
    }
}
